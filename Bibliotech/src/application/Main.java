package application;
	
import javafx.application.Application;
import javafx.stage.Stage;
import javafx.scene.Scene;
import objects.User;

// latest version (comment to differentiate) by daniel

public class Main extends Application {
	private Stage primaryStage;
	private LoginView loginView;
	private MainMenuView mainMenuView;
	private BuyerView buyerView;
	private SellerView sellerView;
	private AdminView adminView;
	private User currentUser;
	
	@Override
	public void start(Stage primaryStage) {
		this.primaryStage = primaryStage;
		this.primaryStage.setTitle("Biblo Tech - Book Management System");
		
		
		
		
		
		// start views initialization
		
		
		
		
		loginView = new LoginView();
		mainMenuView = new MainMenuView();
		adminView = new AdminView();
		
		
		
		
		
		// start with login 
		
		
		
		showLoginView();
	}
	
	private void showLoginView() {
		Scene loginScene = loginView.getScene(primaryStage, this::handleLogin);
		primaryStage.setScene(loginScene);
		primaryStage.show();
	}
	
	private void handleLogin(User user) {
		this.currentUser = user;
		
		
		
		
		//  views related to user
		
		
		buyerView = new BuyerView(user);
		sellerView = new SellerView(user);
		
		showMainMenu();
	}
	
	private void showMainMenu() {
		Scene mainMenuScene = mainMenuView.getScene(
			primaryStage,
			currentUser,
			this::showBuyerView,
			this::showSellerView,
			this::showAdminView,
			this::showLoginView  // logout 
		);
		primaryStage.setScene(mainMenuScene);
	}
	
	
	private void showBuyerView() {
		Scene buyerScene = buyerView.getScene(primaryStage, this::showMainMenu);
		primaryStage.setScene(buyerScene);
	}
	
	
	
	private void showSellerView() {
		Scene sellerScene = sellerView.getScene(primaryStage, this::showMainMenu);
		primaryStage.setScene(sellerScene);
	}
	
	
	
	private void showAdminView() {
		Scene adminScene = adminView.getScene(primaryStage, this::showMainMenu);
		primaryStage.setScene(adminScene);
	}
	
	
	
	public static void main(String[] args) {
		launch(args);
	}
}
