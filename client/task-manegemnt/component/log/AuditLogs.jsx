import { useState, useEffect } from "react";
import PropTypes from "prop-types";

import axios from "axios";

import "./audit.css";

const AuditLogs = ({ token }) => {
  const [logs, setLogs] = useState([]);
  const [loading, setLoading] = useState(true);
  const [error, setError] = useState(null);

  useEffect(() => {
    const fetchLogs = async () => {
      try {
        const response = await axios.get(`http://localhost:8080/api/log/logs`, {
          headers: {
            Authorization: `Bearer ${token}`,
          },
        });
        console.log("Response received: ", response.data);

        // Manually parse the response string
        const logsString = response.data;
        const logs = [
          ...logsString.matchAll(
            /AuditLog\{id=(\d+), user=(\d+), action='([^']+)', timestamp=([^}]+)\}/g
          ),
        ].map(([, id, user, action, timestamp]) => ({
          id: Number(id),
          user,
          action,
          timestamp,
        }));

        setLogs(logs);
      } catch (err) {
        console.error("Error occurred: ", err);
        setError("Failed to fetch logs. Please try again.");
      } finally {
        setLoading(false);
      }
    };

    fetchLogs();
  }, []);

  return (
    <div className="audit-logs-container">
      <h2 className="audit-logs-header">Audit Logs</h2>
      {loading ? (
        <p>Loading...</p>
      ) : error ? (
        <p className="error-message">{error}</p>
      ) : logs.length > 0 ? (
        <div className="audit-logs-table">
          <div className="audit-logs-row audit-logs-header-row">
            <span>ID</span>
            <span>User</span>
            <span>Action</span>
            <span>Timestamp</span>
          </div>
          {logs.map((log) => (
            <div key={log.id} className="audit-logs-row">
              <span>{log.id}</span>
              <span>{log.user}</span>
              <span>{log.action}</span>
              <span>{new Date(log.timestamp).toLocaleString()}</span>
            </div>
          ))}
        </div>
      ) : (
        <p>No logs available</p>
      )}
    </div>
  );
};

AuditLogs.propTypes = {
  token: PropTypes.string.isRequired,
};
export default AuditLogs;
