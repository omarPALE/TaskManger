import { useState, useEffect } from "react";
import axios from "axios";

const TaskPage = ({ token }) => {
  const [tasks, setTasks] = useState([]);
  const [showUpdatePopup, setShowUpdatePopup] = useState(false);
  const [currentTaskId, setCurrentTaskId] = useState(null);
  const [updatedTask, setUpdatedTask] = useState({
    title: "",
    description: "",
    status: "",
    priority: "",
    category: "",
    dueDate: "",
  });

  // Fetch tasks on page load
  useEffect(() => {
    const fetchTasks = async () => {
      try {
        const response = await axios.get("http://localhost:8080/api/task/usertasks", {
          headers: {
            Authorization: `Bearer ${token}`,
          },
        });
        setTasks(response.data);
      } catch (error) {
        console.error("Error fetching tasks:", error);
      }
    };

    fetchTasks();
  }, [token]);

  const handleUpdateInputChange = (e) => {
    const { name, value } = e.target;
    setUpdatedTask((prev) => ({ ...prev, [name]: value }));
  };

  const updateTask = async () => {
    if (!currentTaskId) return;

    // Create a request body with only non-empty fields
    const requestBody = {};
    Object.keys(updatedTask).forEach((key) => {
      if (updatedTask[key]) requestBody[key] = updatedTask[key];
    });

    try {
      const response = await axios.put(
        `http://localhost:8080/api/task/update/${currentTaskId}`,
        requestBody,
        {
          headers: {
            Authorization: `Bearer ${token}`,
            "Content-Type": "application/json",
          },
        }
      );

      if (response.status === 200) {
        setTasks((prevTasks) =>
          prevTasks.map((task) =>
            task.id === currentTaskId ? { ...task, ...requestBody } : task
          )
        );
        setShowUpdatePopup(false);
        setUpdatedTask({
          title: "",
          description: "",
          status: "",
          priority: "",
          category: "",
          dueDate: "",
        });

        // Show success feedback
        alert("Task updated successfully!");
      }
    } catch (error) {
      console.error("Error updating the task:", error);
    }
  };

  const openUpdatePopup = (id) => {
    setCurrentTaskId(id);
    setShowUpdatePopup(true);
  };

  return (
    <div className="task-page">
      {/* Task List */}
      <div className="task-list">
        {tasks.map((task) => (
          <div key={task.id} className="task-card">
            <h3>{task.title}</h3>
            <p>{task.description}</p>
            <button onClick={() => openUpdatePopup(task.id)}>Update</button>
          </div>
        ))}
      </div>

      {/* Popup for Updating Task */}
      {showUpdatePopup && (
        <div className="popup active">
          <button
            className="close-btn"
            onClick={() => setShowUpdatePopup(false)}
          >
            &times;
          </button>
          <div className="task-form">
            <input
              type="text"
              name="title"
              placeholder="New Task Title"
              value={updatedTask.title}
              onChange={handleUpdateInputChange}
            />
            <textarea
              name="description"
              placeholder="New Task Description"
              value={updatedTask.description}
              onChange={handleUpdateInputChange}
            ></textarea>
            <input
              type="text"
              name="category"
              placeholder="New Category"
              value={updatedTask.category}
              onChange={handleUpdateInputChange}
            />
            <input
              type="date"
              name="dueDate"
              value={updatedTask.dueDate}
              onChange={handleUpdateInputChange}
            />
            <select
              name="priority"
              value={updatedTask.priority}
              onChange={handleUpdateInputChange}
            >
              <option value="">Select Priority</option>
              <option value="Low">Low</option>
              <option value="Medium">Medium</option>
              <option value="High">High</option>
            </select>
            <select
              name="status"
              value={updatedTask.status}
              onChange={handleUpdateInputChange}
            >
              <option value="">Select Status</option>
              <option value="Pending">Pending</option>
              <option value="Completed">Completed</option>
              <option value="In Progress">In Progress</option>
            </select>
            <button className="add-btn" onClick={updateTask}>
              Update Task
            </button>
          </div>
        </div>
      )}
    </div>
  );
};

export default TaskPage;
