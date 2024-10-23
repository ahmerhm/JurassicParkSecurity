import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.PasswordAuthentication;
import java.net.ProtocolException;
import java.net.URL;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.Map;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import org.json.JSONArray;
import org.json.JSONObject;

public class Login extends JFrame {

	private static HttpURLConnection connection;

	private static ArrayList<User> Database = new ArrayList<>(); // creates an arrayList of users
	static ArrayList<Badge> allCreatedBadges = new ArrayList<>();
	private static JFrame frame;
	private static JPanel panel;
	private static JLabel userLabel;
	private static JLabel idLabel;
	private static JLabel passResetLabel;
	private static JTextField userText;
	private static JTextField idText;
	private static JLabel passwordLabel;
	private static JPasswordField passwordText;
	private static JPasswordField rPass1;
	private static JPasswordField rPass2;
	private static JButton loginButton;
	private static JButton registerButton;
	private static JButton confirmRegistration;
	private static JButton forgotPassword;
	private static JButton checkBadge;
	private static JButton reset;
	private static JLabel dialogueBox;
	private static JLabel dialogueBox1;

	public static void main(String[] args) {

		// Database reader, where it reads the database from the api endpoint
		BufferedReader reader;
		String line;
		StringBuffer responseContent = new StringBuffer();

		try {
			URL url = new URL("https://sheet.best/api/sheets/764159cf-0583-458a-848a-46b005a768c2");
			connection = (HttpURLConnection) url.openConnection();

			connection.setRequestMethod("GET");
			connection.setConnectTimeout(5000);
			connection.setReadTimeout(5000);

			int status = connection.getResponseCode();

			if (status > 299) {
				reader = new BufferedReader(new InputStreamReader(connection.getErrorStream()));
				while ((line = reader.readLine()) != null) {
					responseContent.append(line);
				}
				reader.close();
			} else {
				reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
				while ((line = reader.readLine()) != null) {
					responseContent.append(line);
				}
				reader.close();
			}
		} catch (MalformedURLException e) {
			dialogueBox.setText("Database not connected!");
		} catch (IOException e) {
			dialogueBox.setText("Database does not contain any users!");
		} finally {
			connection.disconnect();
		}

		parse(responseContent.toString());

		panel = new JPanel();
		panel.setLayout(null);
		panel.setBackground(Color.black);

		frame = new JFrame();// creates a frame panel
		frame.setTitle("Login"); // sets the title of the gui window
		frame.setResizable(true);
		frame.setSize(420, 200); // sets the size of the gui window
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); // closes the application when "x" is clicked
		frame.add(panel); // add panel to frame

		// appearance settings
		ImageIcon parkLogo = new ImageIcon("parkLogo.png"); // imports icon image from project file and stores it in
															// parkLogo
		frame.setIconImage(parkLogo.getImage()); // gets the image from parkLogo and sets it to the window icon
		frame.getContentPane().setBackground(Color.black); // sets the background color to black

		// creates a username label
		userLabel = new JLabel("Username: "); // creates the username label for the login
		userLabel.setForeground(Color.white);
		userLabel.setBounds(10, 20, 80, 25);
		panel.add(userLabel); // adds the label to the panel so it displays

		// creates a text box to enter the username
		userText = new JTextField(20);
		userText.setBounds(100, 20, 165, 25);
		panel.add(userText);

		// creates a username label
		passwordLabel = new JLabel("Password: "); // creates the password label for the login
		passwordLabel.setForeground(Color.white);
		passwordLabel.setBounds(10, 50, 80, 25);// sets dimensions
		panel.add(passwordLabel);

		// creates a text box to enter the password (length of 20)
		passwordText = new JPasswordField(20);
		passwordText.setBounds(100, 50, 165, 25);
		panel.add(passwordText);

		// creates a login button
		loginButton = new JButton("Login");
		loginButton.setBounds(10, 100, 80, 25);
		loginButton.setFocusable(false);
		loginButton.setBackground(Color.red);
		loginButton.addActionListener(new LoginHandler());
		panel.add(loginButton);

		// creates a register button
		registerButton = new JButton("Register");
		registerButton.setBounds(100, 100, 100, 25);
		registerButton.setFocusable(false);
		registerButton.setBackground(Color.red);
		registerButton.addActionListener(new LoginHandler());
		panel.add(registerButton);

		// creates a dialogue label which returns the status of the application to the
		// user to direct them on what to do
		dialogueBox = new JLabel("");
		dialogueBox.setBounds(10, 110, 300, 35);
		dialogueBox.setForeground(Color.white);
		panel.add(dialogueBox);

		// create a new button that calls the forgotPassword method
		forgotPassword = new JButton("I Forgot My Password...");
		forgotPassword.setForeground(Color.blue);
		forgotPassword.setBounds(10, 80, 180, 15);
		forgotPassword.setBackground(Color.black);
		forgotPassword.setBorder(null);
		forgotPassword.addActionListener(new LoginHandler());
		panel.add(forgotPassword);

		frame.setVisible(true); // makes the frame visible

	}

	public static void createVerificationFrame() {
		panel = new JPanel();
		panel.setLayout(null);
		panel.setBackground(Color.black);

		frame = new JFrame();// creates a frame panel
		frame.setTitle("Badge Verification"); // sets the title of the gui window
		frame.setResizable(true);
		frame.setSize(420, 200); // sets the size of the gui window
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); // closes the application when "x" is clicked
		frame.add(panel); // add panel to frame
		// appearance settings
		ImageIcon parkLogo = new ImageIcon("parkLogo.png"); // imports icon image from project file and stores it in pa
		frame.setIconImage(parkLogo.getImage()); // gets the image from parkLogo and sets it to the window icon
		frame.getContentPane().setBackground(Color.black); // sets the background color to black

		idLabel = new JLabel("Enter your Badge #"); // creates the username label for the login
		idLabel.setForeground(Color.white);
		idLabel.setBounds(10, 20, 120, 25);
		panel.add(idLabel); // adds the label to the panel so it displays

		// creates a text box to enter the username
		idText = new JTextField(20);
		idText.setBounds(150, 20, 165, 25);
		panel.add(idText);

		checkBadge = new JButton();
		checkBadge = new JButton("Verify");
		checkBadge.setBounds(100, 80, 200, 25);
		checkBadge.setFocusable(false);
		checkBadge.setBackground(Color.red);
		checkBadge.addActionListener(new LoginHandler());
		panel.add(checkBadge);

		dialogueBox = new JLabel("");
		dialogueBox.setBounds(10, 110, 350, 25);
		dialogueBox.setForeground(Color.white);
		panel.add(dialogueBox);

		frame.setVisible(true);
	}

	public static void createRegisterFrame() {
		panel = new JPanel();
		panel.setLayout(null);
		panel.setBackground(Color.black);

		frame = new JFrame();// creates a frame panel
		frame.setTitle("Registration"); // sets the title of the gui window
		frame.setResizable(true);
		frame.setSize(420, 200); // sets the size of the gui window
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); // closes the application when "x" is clicked
		frame.add(panel); // add panel to frame

		// appearance settings
		ImageIcon parkLogo = new ImageIcon("parkLogo.png"); // imports icon image from project file and stores it in
															// parkLogo
		frame.setIconImage(parkLogo.getImage()); // gets the image from parkLogo and sets it to the window icon
		frame.getContentPane().setBackground(Color.black); // sets the background color to black

		// creates a username label
		userLabel = new JLabel("Username: "); // creates the username label for the login
		userLabel.setForeground(Color.white);
		userLabel.setBounds(10, 20, 80, 25);
		panel.add(userLabel); // adds the label to the panel so it displays

		// creates a text box to enter the username
		userText = new JTextField(20);
		userText.setBounds(100, 20, 165, 25);
		panel.add(userText);

		// creates a username label
		passwordLabel = new JLabel("Password: "); // creates the password label for the login
		passwordLabel.setForeground(Color.white);
		passwordLabel.setBounds(10, 50, 80, 25);// sets dimensions
		panel.add(passwordLabel);

		// creates a text box to enter the password (length of 20)
		passwordText = new JPasswordField(20);
		passwordText.setBounds(100, 50, 165, 25);
		panel.add(passwordText);

		confirmRegistration = new JButton("Confirm Registration");
		confirmRegistration.setBounds(100, 80, 200, 25);
		confirmRegistration.setFocusable(false);
		confirmRegistration.setBackground(Color.red);
		confirmRegistration.addActionListener(new LoginHandler());
		panel.add(confirmRegistration);

		dialogueBox = new JLabel("");
		dialogueBox.setBounds(10, 110, 350, 25);
		dialogueBox.setForeground(Color.white);
		panel.add(dialogueBox);

		frame.setVisible(true); // makes the frame visible
	}

	public static void createPasswordResetFrame() {

		panel = new JPanel();
		panel.setLayout(null);
		panel.setBackground(Color.black);

		frame = new JFrame();// creates a frame panel
		frame.setTitle("Create a New Password"); // sets the title of the gui window
		frame.setResizable(true);
		frame.setSize(420, 220); // sets the size of the gui window
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); // closes the application when "x" is clicked
		frame.add(panel); // add panel to frame

		// appearance settings
		ImageIcon parkLogo = new ImageIcon("parkLogo.png"); // imports icon image from project file and stores it in
		frame.setIconImage(parkLogo.getImage()); // gets the image from parkLogo and sets it to the window icon
		frame.getContentPane().setBackground(Color.black); // sets the background color to black

		passResetLabel = new JLabel("Enter a New Password"); // creates the username label for the login
		passResetLabel.setForeground(Color.white);
		passResetLabel.setBounds(70, 20, 200, 25);
		panel.add(passResetLabel); // adds the label to the panel so it displays

		// creates a text box to enter the password (length of 20)
		rPass1 = new JPasswordField(20);
		rPass1.setBounds(70, 45, 165, 25);
		panel.add(rPass1);

		passResetLabel = new JLabel("Enter it Again"); // creates the username label for the login
		passResetLabel.setForeground(Color.white);
		passResetLabel.setBounds(70, 80, 200, 25);
		panel.add(passResetLabel); // adds the label to the panel so it displays

		// creates a text box to enter the password (length of 20)
		rPass2 = new JPasswordField(20);
		rPass2.setBounds(70, 110, 165, 25);
		panel.add(rPass2);

		// creates a register button
		reset = new JButton("Reset");
		reset.setBounds(250, 110, 100, 25);
		reset.setFocusable(false);
		reset.setBackground(Color.red);
		reset.addActionListener(new LoginHandler());
		panel.add(reset);

		dialogueBox1 = new JLabel("");
		dialogueBox1.setBounds(30, 140, 350, 25);
		dialogueBox1.setForeground(Color.white);
		panel.add(dialogueBox1);


		frame.setVisible(true);
	}

	private static class LoginHandler implements ActionListener {

		public void actionPerformed(ActionEvent e) {

			// different methods of what the different buttons do when they are clicked

			if (e.getSource() == loginButton) { // e.getSource determines which button was clicked
				String user = userText.getText();
				String password = passwordText.getText(); // getting the password field content

				User existingEmployee = new User(user, password);
				allCreatedBadges.add(existingEmployee.getEmployeeBadge());

				boolean result = LoginSuccess(existingEmployee);
				if (result) {
					frame.dispose();
					JurassicParkSecurity program = new JurassicParkSecurity();
				} else
					dialogueBox.setText("Login or Password did not Match");
			}

			else if (e.getSource() == registerButton) {
				frame.dispose();
				createRegisterFrame();

			}

			else if (e.getSource() == confirmRegistration) {

				String user = userText.getText();
				String password = passwordText.getText();

				User newEmployee = new User(user, password);
				allCreatedBadges.add(newEmployee.getEmployeeBadge());

				boolean registered = Register(newEmployee);

				if (registered) {
					dialogueBox.setText("User registered! Close this Window and restart to login!");
					// frame.dispose();
					// printDatabase();
				} else {
					dialogueBox.setText("User did not register, please try again!");
				}
			}

			else if (e.getSource() == forgotPassword) {
				frame.dispose();
				createVerificationFrame();
			}

			else if (e.getSource() == checkBadge) {

				Badge badgeNum = new Badge(idText.getText());

				for(Badge b : allCreatedBadges) {
					
					if (b.equals(badgeNum)) {
						frame.dispose();
						createPasswordResetFrame();
					} else
						dialogueBox.setText("The Badge Number Didn't Match!");
				}
			}

			else if (e.getSource() == reset) {

				Badge badgeNum = new Badge(idText.getText());
				String newPass1 = rPass1.getText();
				String newPass2 = rPass2.getText();

					if(newPass1.equals(newPass2)) {
						try {
							if(resetPassword(badgeNum, newPass2)) {
								dialogueBox1.setText("Password Updated, Please close this window and log back in.");
							} else { dialogueBox1.setText("The Password could not be updated!"); }
						} catch (IOException e1) {
							e1.printStackTrace();
						}
					} else { dialogueBox1.setText("The entered passwords do not match!"); }
				
			}
		}// actionPerformed Method ends

	}// Class LoginHandler ends

	public static boolean LoginSuccess(User employee) { // login method to confirm if the login was successful or not

		boolean result = false;

		for (int u = 0; u < Database.size(); u++) {
			if (Database.get(u).equals(employee)) {
				result = true;
			}
		}
		return result;
	}

	public static boolean Register(User newEmployee) {

		boolean result = false;

		Database.add(newEmployee);

		post(newEmployee);

		for (int u = 0; u < Database.size(); u++) {
			if (Database.get(u).equals(newEmployee)) {
				result = true;
			}
		}
		return result;
	}

	public static boolean resetPassword(Badge badge, String pass) throws IOException {

		boolean result = false;

		for (int i = 0; i < Database.size(); i++) {
			Badge existingBadge = Database.get(i).getEmployeeBadge();
			if (badge.equals(existingBadge)) {
				Database.get(i).setPassword(pass);
				result = true;
				User user = Database.get(i);
				patch(user, pass, i);
			}
		}

		return result;
	}

	public static void printDatabase() {
		for (int i = 0; i < Database.size(); i++) {
			System.out.println(Database.get(i));
		}
	}

	public static void parse(String responseBody) {

		JSONArray users = new JSONArray(responseBody);

		for (int i = 0; i < users.length(); i++) {

			JSONObject user = users.getJSONObject(i);

			if (user != null) {

				String username = user.getString("Username");
				String password = user.getString("Password");
				Badge badgeNum = new Badge(user.getString("Badge"));

				User person = new User(username, password);
				person.setEmployeeBadge(badgeNum);
				allCreatedBadges.add(badgeNum);

				Database.add(person);
			}
		}

		// printDatabase();
	}

	public static void post(User person) {
		try {
			String user = person.getUsername();
			String pass = person.getPassword();
			Badge id = person.getEmployeeBadge();

			URL url = new URL("https://sheet.best/api/sheets/764159cf-0583-458a-848a-46b005a768c2");

			Map<String, Object> params = new LinkedHashMap<>();
			params.put("Username", user);
			params.put("Password", pass);
			params.put("Badge", id);

			StringBuilder dataPost = new StringBuilder();
			for (Map.Entry<String, Object> param : params.entrySet()) {
				if (dataPost.length() != 0)
					dataPost.append('&');
				dataPost.append(URLEncoder.encode(param.getKey(), "UTF-8"));
				dataPost.append('=');
				dataPost.append(URLEncoder.encode(String.valueOf(param.getValue()), "UTF-8"));
			}
			byte[] dataPostBytes = dataPost.toString().getBytes("UTF-8");
			HttpURLConnection connection = (HttpURLConnection) url.openConnection();
			connection.setRequestMethod("POST");
			connection.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
			connection.setRequestProperty("Content-Length", String.valueOf(dataPostBytes.length));
			connection.setDoOutput(true);
			connection.getOutputStream().write(dataPostBytes);

			Reader in = new BufferedReader(new InputStreamReader(connection.getInputStream(), "UTF-8"));

		} catch (MalformedURLException e) {
			dialogueBox.setText("Cannot connect to the database");
		} catch (IOException e) {
			dialogueBox.setText("The User was not resgistered!");
		} finally {
			connection.disconnect();
		}
	}

    //used to change a passowrd/update it
	public static void patch(User user, String pass, int i) throws IOException {
		try {
			Badge badge = user.getEmployeeBadge();
			String username = user.getUsername();
			String id = badge.toString();

			URL url = new URL("https://sheet.best/api/sheets/764159cf-0583-458a-848a-46b005a768c2");

			Map<String, Object> params = new LinkedHashMap<>();
			params.put("Username", username);
			params.put("Password", pass);
			params.put("Badge", id);

			Badge dataBadge = new Badge(params.get("Badge").toString());
			if(dataBadge.equals(badge)) {
				params.put("Password", pass);
			}

			StringBuilder dataPost = new StringBuilder();
			for (Map.Entry<String, Object> param : params.entrySet()) {
				if (dataPost.length() != 0)
					dataPost.append('&');
				dataPost.append(URLEncoder.encode(param.getKey(), "UTF-8"));
				dataPost.append('=');
				dataPost.append(URLEncoder.encode(String.valueOf(param.getValue()), "UTF-8"));
			}
			byte[] dataPostBytes = dataPost.toString().getBytes("UTF-8");
			HttpURLConnection connection = (HttpURLConnection) url.openConnection();
			connection.setRequestMethod("POST");
			connection.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
			connection.setRequestProperty("Content-Length", String.valueOf(dataPostBytes.length));
			connection.setDoOutput(true);
			connection.getOutputStream().write(dataPostBytes);

			Reader in = new BufferedReader(new InputStreamReader(connection.getInputStream(), "UTF-8"));

		} catch (MalformedURLException e) {
			dialogueBox.setText("Cannot connect to the database");
		} catch (IOException e) {
			dialogueBox.setText("The User was not resgistered!");
		} finally {
			connection.disconnect();
		}

		URL url2 = new URL("https://sheet.best/api/sheets/764159cf-0583-458a-848a-46b005a768c2/" + i);
		HttpURLConnection http = (HttpURLConnection) url2.openConnection();
		http.setRequestMethod("DELETE");
		http.setRequestProperty("Accept", "*/*");
		http.setRequestProperty("Authorization", "Bearer mt0dgHmLJMVQhvjpNXDyA83vA_PxH23Y");

		System.out.println(http.getResponseCode() + " " + http.getResponseMessage());
		http.disconnect();

	}
}