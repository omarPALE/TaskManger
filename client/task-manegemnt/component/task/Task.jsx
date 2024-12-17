import { useState } from "react";
import PropTypes from "prop-types";
import axios from "axios";
import "./task.css";

const TaskPage = ({ token }) => {
  const [tasks, setTasks] = useState([]);
  const [showPopup, setShowPopup] = useState(false);
  const [newTask, setNewTask] = useState({
    title: "",
    description: "",
    status: "Pending", // Default status
    priority: "Medium", // Default priority
    category: "",
    dueDate: "",
    userId: 1, // Assuming userId is 1 for now, can be dynamic
  });

  const handleInputChange = (e) => {
    const { name, value } = e.target;
    setNewTask((prev) => ({ ...prev, [name]: value }));
  };

  const addTask = async () => {
    if (newTask.title && newTask.description) {
      console.log("token is:", token);
      try {
        // Ensure the date is in the correct format
        const formattedDate = new Date(newTask.dueDate)
          .toISOString()
          .split("T")[0];

        const response = await axios.post(
          "http://localhost:8080/api/task/addtask",
          { ...newTask, dueDate: formattedDate }, // Format the dueDate
          {
            headers: {
              Authorization: `Bearer ${token}`,
              "Content-Type": "application/json",
            },
          }
        );

        if (response.status === 200) {
          setTasks([
            ...tasks,
            { ...newTask, id: Date.now(), completed: false },
          ]);
          setNewTask({
            title: "",
            description: "",
            status: "Pending",
            priority: "Medium",
            category: "",
            dueDate: "",
            userId: 1,
          });
          setShowPopup(false);
        }
      } catch (error) {
        console.error("There was an error adding the task:", error);
      }
    }
  };

  const deleteTask = (id) => {
    setTasks(tasks.filter((task) => task.id !== id));
  };

  const updateTask = (id, updatedDetails) => {
    setTasks(
      tasks.map((task) =>
        task.id === id ? { ...task, ...updatedDetails } : task
      )
    );
  };

  const toggleComplete = (id) => {
    setTasks(
      tasks.map((task) =>
        task.id === id ? { ...task, completed: !task.completed } : task
      )
    );
  };

  return (
    <div className="task-page">
      {/* Navbar */}
      <div className="navbar">
        <h1>Task Management</h1>
        <button className="create-task-btn" onClick={() => setShowPopup(true)}>
          Create Task
        </button>
      </div>

      {/* Main Content */}
      <div className="main-content">
        {/* Task List */}
        <div className="task-list">
          {tasks.map((task) => (
            <div
              key={task.id}
              className={`task-card ${task.completed ? "completed" : ""}`}
            >
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
                <button onClick={() => toggleComplete(task.id)}>
                  {task.completed ? "Mark Incomplete" : "Mark Complete"}
                </button>
                <button onClick={() => updateTask(task.id, newTask)}>
                  Update
                </button>
                <button onClick={() => deleteTask(task.id)}>Delete</button>
              </div>
            </div>
          ))}
        </div>
      </div>

      {/* Popup for Creating Tasks */}
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
              value={newTask.title}
              onChange={handleInputChange}
            />
            <textarea
              name="description"
              placeholder="Task Description"
              value={newTask.description}
              onChange={handleInputChange}
            ></textarea>
            <input
              type="text"
              name="category"
              placeholder="Category"
              value={newTask.category}
              onChange={handleInputChange}
            />
            <input
              type="date"
              name="dueDate"
              value={newTask.dueDate}
              onChange={handleInputChange}
            />
            <select
              name="priority"
              value={newTask.priority}
              onChange={handleInputChange}
            >
              <option value="">Select Priority</option>
              <option value="Low">Low</option>
              <option value="Medium">Medium</option>
              <option value="High">High</option>
            </select>
            <select
              name="status"
              value={newTask.status}
              onChange={handleInputChange}
            >
              <option value="Pending">Pending</option>
              <option value="Completed">Completed</option>
              <option value="In Progress">In Progress</option>
            </select>
            <button className="add-btn" onClick={addTask}>
              Add Task
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
