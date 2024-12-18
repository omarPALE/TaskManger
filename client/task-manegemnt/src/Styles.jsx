const Styles = {
  root: {
    maxWidth: "1280px",
    margin: "0 auto",
    padding: "2rem",
    textAlign: "center",
  },
  logo: {
    height: "6em",
    padding: "1.5em",
    willChange: "filter",
    transition: "filter 300ms",
  },
  logoHover: {
    filter: "drop-shadow(0 0 2emrgba(0, 0, 2, 0.67))",
  },
  logoReactHover: {
    filter: "drop-shadow(0 0 2em #61dafbaa)",
  },
  card: {
    padding: "2em",
  },
  readTheDocs: {
    color: "#888",
  },
  navbar: {
    display: "flex",
    justifyContent: "space-between",
    alignItems: "center",
    padding: "1rem 2rem",
    backgroundColor: "#4caf50",
    color: "white",
    boxShadow: "0 4px 6px rgba(0, 0, 0, 0.1)",
    position: "sticky",
    top: "0",
    zIndex: 1000,
    width: "100%",
    overflowX: "auto",
    listStyleType: "none",
  },
  navbarLogo: {
    fontSize: "1.5rem",
    fontWeight: "bold",
  },
  navbarLinks: {
    display: "flex",
    gap: "1.5rem",
    listStyle: "none",
  },
  navbarLink: {
    textDecoration: "none",
    color: "white",
    fontSize: "1rem",
    transition: "color 0.3s ease",
  },
  navbarLinkHover: {
    color: "#000000",
  },
  navbarLinksActive: {
    display: "flex",
  },
};

export default Styles;
