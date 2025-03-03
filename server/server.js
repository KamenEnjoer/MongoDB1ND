require('dotenv').config();
const express = require('express');
const mongoose = require('mongoose');
const cors = require('cors');

const app = express();
app.use(cors());
app.use(express.json());

mongoose.connect(process.env.MONGO_URI, {
    useNewUrlParser: true,
    useUnifiedTopology: true
}).then(() => console.log("Connected to MongoDB"))
  .catch(err => console.log("Connection error:", err));

const userSchema = new mongoose.Schema({
    name: String
});
const User = mongoose.model('User', userSchema);

app.get("/get_names", async (req, res) => {
    try {
        const data = await Bandimas1.find({}, "name"); // Только имена
        const names = data.map(item => item.name); 
        res.json(names);
    } catch (error) {
        res.status(500).json({ error: "Server error" });
    }
});

const PORT = process.env.PORT || 3000;
app.listen(PORT, () => console.log(`Servers port: ${PORT}`));
