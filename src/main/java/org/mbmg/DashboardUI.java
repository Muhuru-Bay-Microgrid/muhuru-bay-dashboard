package org.mbmg;

import com.vaadin.annotations.Theme;
import com.vaadin.annotations.Title;
import com.vaadin.event.ShortcutAction;
import com.vaadin.event.ShortcutListener;
import com.vaadin.event.dd.DragAndDropEvent;
import com.vaadin.event.dd.DropHandler;
import com.vaadin.event.dd.acceptcriteria.AcceptCriterion;
import com.vaadin.navigator.Navigator;
import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener;
import com.vaadin.server.Page;
import com.vaadin.server.ThemeResource;
import com.vaadin.server.VaadinRequest;
import com.vaadin.shared.ui.label.ContentMode;
import com.vaadin.ui.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.vaadin.spring.VaadinUI;
import org.vaadin.spring.navigator.SpringViewProvider;

import java.util.HashMap;
import java.util.Locale;

/**
 * Created by rpomeroy on 5/8/14.
 */
@VaadinUI
@Theme("dashboard")
@Title("Muhuru-Bay Microgrid Dashboard")
class DashboardUI extends UI {

    private static final long serialVersionUID = 1L;
    CssLayout root = new CssLayout();
    VerticalLayout loginLayout;
    CssLayout content = new CssLayout();
    @Autowired
    ErrorView errorView;
    HashMap<String, Button> viewNameToMenuButton = new HashMap<String, Button>();
    HashMap<String, Class<? extends View>> routes = new HashMap<String, Class<? extends View>>() {
        {
            put("/dashboard", DashboardView.class);
        }
    };
    private Navigator nav;
    private HelpManager helpManager;

    @Override
    protected void init(VaadinRequest vaadinRequest) {
        helpManager = new HelpManager(this);
        setLocale(Locale.US);
        setContent(root);
        root.addStyleName("root");
        root.setSizeFull();
        // Unfortunate to use an actual widget here, but since CSS generated
        // elements can't be transitioned yet, we must
        Label bg = new Label();
        bg.setSizeUndefined();
        bg.addStyleName("login-bg");
        root.addComponent(bg);
        buildLoginView(false);
    }

    private void buildLoginView(boolean exit) {
        if (exit) {
            root.removeAllComponents();
        }
        helpManager.closeAll();
        HelpOverlay helpOverlay = helpManager
                .addOverlay(
                        "Welcome to the Muhuru-Bay Microgrid Dashboard",
                        "<p>No username or password is required to view the dashboard. <br>" +
                                "Just click the ‘Sign In’ button to continue.</p><p>Administrators, " +
                                "please sign in",
                        "login"
                );
        helpOverlay.center();
        addWindow(helpOverlay);
        addStyleName("login");
        loginLayout = new VerticalLayout();
        loginLayout.setSizeFull();
        loginLayout.addStyleName("login-layout");
        root.addComponent(loginLayout);

        final CssLayout loginPanel = new CssLayout();
        loginPanel.addStyleName("login-panel");

        HorizontalLayout labels = new HorizontalLayout();
        labels.setWidth("100%");
        labels.setMargin(true);
        labels.addStyleName("labels");
        loginPanel.addComponent(labels);

        Label welcome = new Label("Muhuru-Bay Microgrid DashBoard");
        welcome.setSizeUndefined();
        welcome.addStyleName("h2");
        welcome.addStyleName("light");
        labels.addComponent(welcome);
        labels.setComponentAlignment(welcome, Alignment.MIDDLE_LEFT);

        HorizontalLayout fields = new HorizontalLayout();
        fields.setSpacing(true);
        fields.setMargin(true);
        fields.addStyleName("fields");

        final TextField username = new TextField("Username");
        username.focus();
        fields.addComponent(username);
        final PasswordField password = new PasswordField("Password");
        fields.addComponent(password);
        final Button signinButton = new Button("Sign In");
        signinButton.addStyleName("default");
        fields.addComponent(signinButton);
        fields.setComponentAlignment(signinButton, Alignment.BOTTOM_LEFT);

        final ShortcutListener enter = new ShortcutListener("Sign In",
                ShortcutAction.KeyCode.ENTER, null) {
            @Override
            public void handleAction(Object sender, Object target) {
                signinButton.click();
            }
        };

        signinButton.addClickListener(event -> {
            if (username.getValue() != null
                    && username.getValue().equals("")
                    && password.getValue() != null
                    && password.getValue().equals("")) {
                signinButton.removeShortcutListener(enter);
                buildMainView();
            } else {
                if (loginPanel.getComponentCount() > 2) {
                    // Remove the previous error message
                    loginPanel.removeComponent(loginPanel.getComponent(2));
                }
                // Add new error message
                Label error = new Label(
                        "Wrong username or password. <span>Hint: try empty values</span>",
                        ContentMode.HTML);
                error.addStyleName("error");
                error.setSizeUndefined();
                error.addStyleName("light");
                // Add animation
                error.addStyleName("v-animate-reveal");
                loginPanel.addComponent(error);
                username.focus();
            }
        });
        signinButton.addShortcutListener(enter);
        loginPanel.addComponent(fields);
        loginLayout.addComponent(loginPanel);
        loginLayout.setComponentAlignment(loginPanel, Alignment.MIDDLE_CENTER);
    }


    private void buildMainView() {

        nav = new Navigator(this, content);
        for (String route : routes.keySet()) {
            nav.addView(route, routes.get(route));
        }

        helpManager.closeAll();
        removeStyleName("login");
        root.removeComponent(loginLayout);

        root.addComponent(new HorizontalLayout() {
            {
                setSizeFull();
                addStyleName("main-view");
                addComponent(new VerticalLayout() {
                    // Sidebar
                    {
                        addStyleName("sidebar");
                        setWidth(null);
                        setHeight("100%");

                        // Branding element
                        addComponent(new CssLayout() {
                            {
                                addStyleName("branding");
                                Label logo = new Label(
                                        "<span>Muhuru-Bay</span> Dashboard",
                                        ContentMode.HTML);
                                logo.setSizeUndefined();
                                addComponent(logo);
                            }
                        });

                        // User menu
                        addComponent(new VerticalLayout() {
                            {
                                setSizeUndefined();
                                addStyleName("user");
                                Image profilePic = new Image(
                                        null,
                                        new ThemeResource("img/profile-pic.png"));
                                profilePic.setWidth("34px");
                                addComponent(profilePic);

                                MenuBar.Command cmd = new MenuBar.Command() {
                                    @Override
                                    public void menuSelected(
                                            MenuBar.MenuItem selectedItem) {
                                        Notification
                                                .show("Not implemented in this demo");
                                    }
                                };
                                MenuBar settings = new MenuBar();
                                MenuBar.MenuItem settingsMenu = settings.addItem("",
                                        null);
                                settingsMenu.setStyleName("icon-cog");
                                settingsMenu.addItem("Settings", cmd);
                                settingsMenu.addItem("Preferences", cmd);
                                settingsMenu.addSeparator();
                                settingsMenu.addItem("My Account", cmd);
                                addComponent(settings);

                                Button exit = new NativeButton("Exit");
                                exit.addStyleName("icon-cancel");
                                exit.setDescription("Sign Out");
                                addComponent(exit);
                                exit.addClickListener(new Button.ClickListener() {
                                    @Override
                                    public void buttonClick(Button.ClickEvent event) {
                                        buildLoginView(true);
                                    }
                                });
                            }
                        });
                    }
                });
                // Content
                addComponent(content);
                content.setSizeFull();
                content.addStyleName("view-content");
                setExpandRatio(content, 1);
            }

        });

        for (final String view : new String[]{"dashboard", "sales",
                "transactions", "reports", "schedule"}) {
            Button b = new NativeButton(view.substring(0, 1).toUpperCase()
                    + view.substring(1).replace('-', ' '));
            b.addStyleName("icon-" + view);
            b.addClickListener(new Button.ClickListener() {
                @Override
                public void buttonClick(Button.ClickEvent event) {
                    event.getButton().addStyleName("selected");
                    if (!nav.getState().equals("/" + view))
                        nav.navigateTo("/" + view);
                }
            });


            viewNameToMenuButton.put("/" + view, b);
        }

        viewNameToMenuButton.get("/dashboard").setHtmlContentAllowed(true);
        viewNameToMenuButton.get("/dashboard").setCaption(
                "Dashboard<span class=\"badge\">2</span>");

        String f = Page.getCurrent().getUriFragment();
        if (f != null && f.startsWith("!")) {
            f = f.substring(1);
        }
        if (f == null || f.equals("") || f.equals("/")) {
            nav.navigateTo("/dashboard");
            helpManager.showHelpFor(DashboardView.class);
        } else {
            nav.navigateTo(f);
            helpManager.showHelpFor(routes.get(f));
            viewNameToMenuButton.get(f).addStyleName("selected");
        }

        nav.addViewChangeListener(new ViewChangeListener() {

            @Override
            public boolean beforeViewChange(ViewChangeEvent event) {
                helpManager.closeAll();
                return true;
            }

            @Override
            public void afterViewChange(ViewChangeEvent event) {
                View newView = event.getNewView();
                helpManager.showHelpFor(newView);
            }
        });

    }
}
