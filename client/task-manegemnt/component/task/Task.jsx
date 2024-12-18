import { useState, useEffect } from "react";
import PropTypes from "prop-types";
import axios from "axios";
import "./task.css";

const TaskPage = ({ token }) => {
  const [tasks, setTasks] = useState([]);
  const [showPopup, setShowPopup] = useState(false);
  const [feedbackMessage, setFeedbackMessage] = useState("");
  const [editTask, setEditTask] = useState(null); // Track task being edited
  const [updatedFields, setUpdatedFields] = useState({});

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
  }, [token]);

  const handleInputChange = (e) => {
    const { name, value } = e.target;
    setUpdatedFields((prev) => ({ ...prev, [name]: value }));
  };

  const openEditPopup = (task) => {
    setEditTask(task);
    setUpdatedFields(task);
    setShowPopup(true);
  };

  const updateTask = async () => {
    if (!editTask) return;

    const updatedData = { ...editTask, ...updatedFields };
    try {
      const response = await axios.put(
        `http://localhost:8080/api/task/update/${editTask.id}`,
        updatedData,
        {
          headers: {
            Authorization: `Bearer ${token}`,
            "Content-Type": "application/json",
          },
        }
      );

      if (response.status === 200) {
        setTasks(
          tasks.map((task) => (task.id === editTask.id ? response.data : task))
        );
        setFeedbackMessage("Task updated successfully!");
        setTimeout(() => setFeedbackMessage(""), 4000);
      } else {
        console.error("Failed to update task:", response.statusText);
      }
    } catch (error) {
      console.error("Error occurred while updating the task:", error);
      setFeedbackMessage("Failed to update the task!");
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
        setTasks(tasks.filter((task) => task.id !== id));
        setFeedbackMessage("Task deleted successfully!");
        setTimeout(() => setFeedbackMessage(""), 4000);
      }
    } catch (error) {
      console.error("Error occurred while deleting the task:", error);
    }
  };

  return (
    <div className="task-page">
      <div className="navbar">
        <h1>Task Management</h1>
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
                <button onClick={() => openEditPopup(task)}>Update</button>
                <button onClick={() => deleteTask(task.id)}>Delete</button>
              </div>
            </div>
          ))}
        </div>
      </div>

      {showPopup && editTask && (
        <div className="popup active">
          <button className="close-btn" onClick={() => setShowPopup(false)}>
            &times;
          </button>
          <div className="task-form">
            <input
              type="text"
              name="title"
              placeholder="Task Title"
              value={updatedFields.title || ""}
              onChange={handleInputChange}
            />
            <textarea
              name="description"
              placeholder="Task Description"
              value={updatedFields.description || ""}
              onChange={handleInputChange}
            ></textarea>
            <input
              type="text"
              name="category"
              placeholder="Category"
              value={updatedFields.category || ""}
              onChange={handleInputChange}
            />
            <input
              type="date"
              name="dueDate"
              value={updatedFields.dueDate || ""}
              onChange={handleInputChange}
            />
            <select
              name="priority"
              value={updatedFields.priority || ""}
              onChange={handleInputChange}
            >
              <option value="Low">Low</option>
              <option value="Medium">Medium</option>
              <option value="High">High</option>
            </select>
            <select
              name="status"
              value={updatedFields.status || ""}
              onChange={handleInputChange}
            >
              <option value="Pending">Pending</option>
              <option value="Completed">Completed</option>
              <option value="In Progress">In Progress</option>
            </select>
            <button className="update-btn" onClick={updateTask}>
              Update Task
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
