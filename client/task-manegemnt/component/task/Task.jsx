import { useState, useEffect } from "react";
import PropTypes from "prop-types";
import axios from "axios";
import "./task.css";

const TaskPage = ({ token }) => {
  const [tasks, setTasks] = useState([]);
  const [showPopup, setShowPopup] = useState(false);
  const [isEditing, setIsEditing] = useState(false);
  const [feedbackMessage, setFeedbackMessage] = useState("");
  const [currentTask, setCurrentTask] = useState(null);
  const [taskDetails, setTaskDetails] = useState({
    title: "",
    description: "",
    status: "Pending",
    priority: "Medium",
    category: "",
    dueDate: "",
  });
  const [reload, setReload] = useState(false);

  // Fetch tasks on page load
  useEffect(() => {
    const fetchTasks = async () => {
      try {
        const response = await axios.get(
          "http://localhost:8080/api/task/usertasks",
          {
            headers: {
              Authorization: `Bearer ${token}`,
            },
          }
        );
        setTasks(response.data);
      } catch (error) {
        console.error("Error fetching tasks:", error);
      }
    };

    fetchTasks();
  }, [reload, token]);

  const handleInputChange = (e) => {
    const { name, value } = e.target;
    setTaskDetails((prev) => ({ ...prev, [name]: value }));
  };

  const openPopup = (task = null) => {
    setIsEditing(!!task);
    setCurrentTask(task);
    setTaskDetails(
      task || {
        title: "",
        description: "",
        status: "Pending",
        priority: "Medium",
        category: "",
        dueDate: "",
      }
    );
    setShowPopup(true);
  };

  const saveTask = async () => {
    try {
      const endpoint = isEditing
        ? `http://localhost:8080/api/task/update/${currentTask.id}`
        : "http://localhost:8080/api/task/addtask";

      const method = isEditing ? "put" : "post";

      const response = await axios[method](endpoint, taskDetails, {
        headers: {
          Authorization: `Bearer ${token}`,
          "Content-Type": "application/json",
        },
      });

      if (response.status === 200 || response.status === 201) {
        if (isEditing) {
          setTasks(
            tasks.map((task) =>
              task.id === currentTask.id ? response.data : task
            )
          );
          setReload(!reload);
        } else {
          setReload(!reload);
          setTasks([...tasks, response.data]);
        }

        setFeedbackMessage(
          isEditing ? "Task updated successfully!" : "Task added successfully!"
        );
        setTimeout(() => setFeedbackMessage(""), 4000);
      }
    } catch (error) {
      console.error("Error saving task:", error);
      setFeedbackMessage("Failed to save the task!");
      setTimeout(() => setFeedbackMessage(""), 4000);
    } finally {
      setShowPopup(false);
    }
  };

  const deleteTask = async (id) => {
    try {
      const response = await axios.delete(
        `http://localhost:8080/api/task/delete/${id}`,
        {
          headers: {
            Authorization: `Bearer ${token}`,
          },
        }
      );

      if (response.status === 200) {
        setReload(!reload);
        setTasks(tasks.filter((task) => task.id !== id));
        setFeedbackMessage("Task deleted successfully!");
        setTimeout(() => setFeedbackMessage(""), 4000);
      }
    } catch (error) {
      console.error("Error deleting task:", error);
      setFeedbackMessage("Failed to delete the task!");
      setTimeout(() => setFeedbackMessage(""), 4000);
    }
  };

  return (
    <div className="task-page">
      <div className="navbar">
        <h1>Task Management</h1>
        <button className="create-task-btn" onClick={() => openPopup()}>
          Create Task
        </button>
      </div>

      <div className="main-content">
        {feedbackMessage && (
          <div className="feedback-popup">{feedbackMessage}</div>
        )}
        <div className="task-list">
          {tasks.map((task) => (
            <div key={task.id} className="task-card">
              <h3>{task.title}</h3>
              <p>{task.description}</p>
              <p>
                <strong>Category:</strong> {task.category}
              </p>
              <p>
                <strong>Due Date:</strong> {task.dueDate}
              </p>
              <p>
                <strong>Priority:</strong> {task.priority}
              </p>
              <p>
                <strong>Status:</strong> {task.status}
              </p>
              <div className="task-actions">
                <button onClick={() => openPopup(task)}>Update</button>
                <button onClick={() => deleteTask(task.id)}>Delete</button>
              </div>
            </div>
          ))}
        </div>
      </div>

      {showPopup && (
        <div className="popup active">
          <button className="close-btn" onClick={() => setShowPopup(false)}>
            &times;
          </button>
          <div className="task-form">
            <input
              type="text"
              name="title"
              placeholder="Task Title"
              value={taskDetails.title}
              onChange={handleInputChange}
            />
            <textarea
              name="description"
              placeholder="Task Description"
              value={taskDetails.description}
              onChange={handleInputChange}
            ></textarea>
            <input
              type="text"
              name="category"
              placeholder="Category"
              value={taskDetails.category}
              onChange={handleInputChange}
            />
            <input
              type="date"
              name="dueDate"
              value={taskDetails.dueDate}
              onChange={handleInputChange}
            />
            <select
              name="priority"
              value={taskDetails.priority}
              onChange={handleInputChange}
            >
              <option value="Low">Low</option>
              <option value="Medium">Medium</option>
              <option value="High">High</option>
            </select>
            <select
              name="status"
              value={taskDetails.status}
              onChange={handleInputChange}
            >
              <option value="Pending">Pending</option>
              <option value="Completed">Completed</option>
              <option value="In Progress">In Progress</option>
            </select>
            <button className="save-btn" onClick={saveTask}>
              {isEditing ? "Update Task" : "Add Task"}
            </button>
          </div>
        </div>
      )}
    </div>
  );
};

TaskPage.propTypes = {
  token: PropTypes.string.isRequired,
};

export default TaskPage;
