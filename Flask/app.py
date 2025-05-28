from flask import Flask

app = Flask(__name__)  # Create app instance

@app.route("/")
def home():
    return "Hello, Flask!"

if __name__ == "__main__":
    app.run(debug=True)