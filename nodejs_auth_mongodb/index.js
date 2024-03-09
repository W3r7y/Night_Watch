// Import package
const mongoose = require('mongoose');
const bcrypt = require('bcrypt');
var express = require('express');
var bodyParser = require("body-parser");

var app = express();
app.use(bodyParser.json());
app.use(bodyParser.urlencoded({extended: true}));

//Connection URL
var url = 'mongodb://127.0.0.1:27017/NightWatchersDB' // default port
mongoose.connect(url, {useNewUrlParser: true});

const userSchema = new mongoose.Schema({
    email: {
      type: String,
      required: true,
      unique: true, // Ensures email uniqueness
      trim: true // Removes whitespace from beginning and end
    },
    name: {
      type: String,
      required: true,
      trim: true
    },
    passwordHash: { // Store the hashed password
      type: String,
      required: true
    }
  });

  const User = mongoose.model('User', userSchema);


// Route for user registration
app.post('/register', async (req, res) => {
    try {
      // Extract user data from request body
      const { email, password, name } = req.body;
  
      // Basic validation (replace with more comprehensive validation)
      if (!email || !password || !name) {
        return res.status(400).json({ message: "Missing required fields." });
      }
  
      // Check if email already exists
      const existingUser = await User.findOne({ email });
      if (existingUser) {
        return res.status(409).json({ message: "Email already exists." });
      }

        // Hash the password with bcrypt (recommended over custom function)
    const salt = await bcrypt.genSalt(10); // Generate a random salt
    const hashedPassword = await bcrypt.hash(password, salt);

    
      // Create a new user document
      const newUser = new User({
        email,
        name,
        passwordHash: hashedPassword
      });
  
      // Save the new user to the database
      await newUser.save();
  
      // Send success response (avoid sending sensitive data like password)
      res.status(201).json({ message: "Registration successful!" });
    } catch (error) {
      console.error(error);
      res.status(500).json({ message: "Registration failed." });
    }
  });
  

// Route for user login
app.post('/login', async (req, res) => {
    try {
      // Extract email and password from request body
      const { email, password } = req.body;
  
      // Basic validation (replace with more comprehensive validation)
      if (!email || !password) {
        return res.status(400).json({ message: "Missing required fields." });
      }
  
      // Check if email exists
      const existingUser = await User.findOne({ email });
      if (!existingUser) {
        return res.status(401).json({ message: "Email does not exist." });
      }
  
      // Compare password (don't log stored password hash)
      const isMatch = await bcrypt.compare(password, existingUser.passwordHash);
      if (isMatch) {
        // Login successful (avoid sending sensitive data like password)
        res.status(200).json({ message: "Login successful!" });
      } else {
        res.status(401).json({ message: "Wrong password." });
      }
    } catch (error) {
      console.error(error);
      res.status(500).json({ message: "Login failed." });
    }
  });

//Start Web Server        
app.listen(3000, function(){
    console.log("Connected to MongoDB Server, Web Service running on port 3000");
});