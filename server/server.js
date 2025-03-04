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

const bandimasSchema = new mongoose.Schema({
    name: String
});
const bandimas1 = mongoose.model('bandimas1', bandimasSchema);

app.get("/get_items", async (req, res) => {
    try {
        const data = await bandimas1.find({}, "_id name something"); 
        res.json(data); 
    } catch (error) {
        console.error("Error fetching data:", error);
        res.status(500).json({ error: "Server error" });
    }
});

app.delete("/delete_item/:id", async (req, res) => {
    try {
        const { id } = req.params;
        const result = await bandimas1.findByIdAndDelete(id);
        
        if (!result) {
            return res.status(404).json({ error: "Item not found" });
        }

        res.json({ message: "Item deleted successfully" });
    } catch (error) {
        console.error("Error deleting item:", error);
        res.status(500).json({ error: "Server error" });
    }
});

app.put("/update_item/:id", async (req, res) => {
    try {
        console.log("Request received:", req.params.id, req.body);
        
        const { id } = req.params;
        const { something } = req.body;

        const updatedItem = await bandimas1.findByIdAndUpdate(
            id,
            { something: something },
            { new: true }
        );

        if (!updatedItem) {
            return res.status(404).json({ error: "Item not found" });
        }

        res.json({ message: "Item updated successfully", updatedItem });
    } catch (error) {
        console.error("Error updating item:", error);
        res.status(500).json({ error: "Server error" });
    }
});


const PORT = process.env.PORT || 3000;
app.listen(PORT, () => console.log(`Server running on port: ${PORT}`));
