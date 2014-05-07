/*
 * Copyright 2014 The original authors
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *   http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.mbmg;

import com.vaadin.annotations.Theme;
import com.vaadin.annotations.Title;
import com.vaadin.event.ShortcutAction;
import com.vaadin.event.ShortcutListener;
import com.vaadin.navigator.Navigator;
import com.vaadin.server.VaadinRequest;
import com.vaadin.shared.ui.label.ContentMode;
import com.vaadin.ui.*;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.context.web.SpringBootServletInitializer;
import org.springframework.context.annotation.ComponentScan;
import org.vaadin.spring.VaadinUI;

import java.util.Locale;

/**
 * Entry point into the Vaadin web application. You may run this from
 * {@code public static void main} or change the Maven {@code packaging} to
 * {@code war} and deploy to any Servlet 3 container, Java code unchanged.
 *
 * @author Petter Holmström (petter@vaadin.com)
 * @author Josh Long (josh@joshlong.com)
 */
@EnableAutoConfiguration
@ComponentScan
public class DashboardApplication extends SpringBootServletInitializer {

    public static void main(String[] args) throws Exception {
        SpringApplication.run(DashboardApplication.class, args);
    }

    @Override
    protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
        return application.sources(DashboardApplication.class);
    }
}

@VaadinUI
@Theme("dashboard")
@Title("Muhuru-Bay Microgrid Dashboard")
class RootUI extends UI {

    CssLayout root = new CssLayout();
    VerticalLayout loginLayout;
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
        HelpOverlay w = helpManager
                .addOverlay(
                        "Welcome to the Muhuru-Bay Microgrid Dashboard",
                        "<p>No username or password is required to view the dashboard. <br>" +
                                "Just click the ‘Sign In’ button to continue.</p><p>Administrators, " +
                                "please sign in",
                        "login"
                );
        w.center();
        addWindow(w);
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

//        Label title = new Label("Muhuru-Bay Micrgrid Dashboard");
//        title.setSizeUndefined();
//        title.addStyleName("h2");
//        title.addStyleName("light");
//        labels.addComponent(title);
//        labels.setComponentAlignment(title, Alignment.MIDDLE_LEFT);

        HorizontalLayout fields = new HorizontalLayout();
        fields.setSpacing(true);
        fields.setMargin(true);
        fields.addStyleName("fields");

        final TextField username = new TextField("Username");
        username.focus();
        fields.addComponent(username);

        final PasswordField password = new PasswordField("Password");
        fields.addComponent(password);

        final Button signin = new Button("Sign In");
        signin.addStyleName("default");
        fields.addComponent(signin);
        fields.setComponentAlignment(signin, Alignment.BOTTOM_LEFT);

        final ShortcutListener enter = new ShortcutListener("Sign In",
                ShortcutAction.KeyCode.ENTER, null) {
            @Override
            public void handleAction(Object sender, Object target) {
                signin.click();
            }
        };

        signin.addClickListener(new Button.ClickListener() {
            @Override
            public void buttonClick(Button.ClickEvent event) {
                if (username.getValue() != null
                        && username.getValue().equals("")
                        && password.getValue() != null
                        && password.getValue().equals("")) {
                    signin.removeShortcutListener(enter);
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
            }
        });

        signin.addShortcutListener(enter);

        loginPanel.addComponent(fields);

        loginLayout.addComponent(loginPanel);
        loginLayout.setComponentAlignment(loginPanel, Alignment.MIDDLE_CENTER);
    }

    private void buildMainView() {

    }

}
