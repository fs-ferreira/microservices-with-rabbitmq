import { Sequelize } from "sequelize";

const db = new Sequelize("auth_db", "admin", "admin123", {
  host: "localhost",
  port: 5432,
  dialect: "postgres",
  quoteIdentifiers: false,
  define: {
    timestamps: false,
    underscored: true,
    freezeTableName: true
  }
})

db.authenticate().then(() => {
  console.info("Connection with database has been stablished")
})
  .catch((err) => {
    console.error("Unable to connect to database", err.message)
  })

export default db;

