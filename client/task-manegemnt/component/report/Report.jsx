import { useLocation, useNavigate } from "react-router-dom";
import "./report.css";

const Report = () => {
  const location = useLocation();
  const navigate = useNavigate();
  const tasks = location.state?.tasks || []; // Access tasks from state

  const reportData = {
    completed: tasks.filter((task) => task.status === "Completed").length,
    pending: tasks.filter((task) => task.status === "Pending").length,
    inProgress: tasks.filter((task) => task.status === "In Progress").length,
    priorityGroups: {
      Low: tasks.filter((task) => task.priority === "Low").length,
      Medium: tasks.filter((task) => task.priority === "Medium").length,
      High: tasks.filter((task) => task.priority === "High").length,
    },
    categoryGroups: tasks.reduce((acc, task) => {
      acc[task.category] = acc[task.category] ? acc[task.category] + 1 : 1;
      return acc;
    }, {}),
  };

  return (
    <div className="report-page">
      <button className="close-report-btn" onClick={() => navigate(-1)}>
        Close Report
      </button>

      <h1>Task Report</h1>

      <div className="report-section">
        <h2>Task Completion Status</h2>
        <p>Completed Tasks: {reportData.completed}</p>
        <p>Pending Tasks: {reportData.pending}</p>
        <p>Tasks In Progress: {reportData.inProgress}</p>
      </div>

      <div className="report-section">
        <h2>Task Priority Breakdown</h2>
        <p>Low Priority: {reportData.priorityGroups.Low}</p>
        <p>Medium Priority: {reportData.priorityGroups.Medium}</p>
        <p>High Priority: {reportData.priorityGroups.High}</p>
      </div>

      <div className="report-section">
        <h2>Tasks by Category</h2>
        {Object.keys(reportData.categoryGroups).length > 0 ? (
          Object.entries(reportData.categoryGroups).map(([category, count]) => (
            <p key={category}>
              {category}: {count}
            </p>
          ))
        ) : (
          <p>No categories available</p>
        )}
      </div>
    </div>
  );
};

export default Report;
