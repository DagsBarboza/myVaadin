package com.example.myvaadinwithdatabase;

import java.sql.SQLException;

import com.vaadin.annotations.Theme;
import com.vaadin.data.util.sqlcontainer.SQLContainer;
import com.vaadin.data.util.sqlcontainer.connection.JDBCConnectionPool;
import com.vaadin.data.util.sqlcontainer.connection.SimpleJDBCConnectionPool;
import com.vaadin.data.util.sqlcontainer.query.TableQuery;
import com.vaadin.navigator.Navigator;
import com.vaadin.server.VaadinRequest;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Label;
import com.vaadin.ui.UI;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.themes.Reindeer;

/**
 * Main UI class
 */
@Theme("myvaadinwithdatabasetheme")
@SuppressWarnings("serial")
public class MyvaadinwithdatabaseUI extends UI {

	private SQLContainer createContainer(){
		try{
			final JDBCConnectionPool pool = new SimpleJDBCConnectionPool("com.mysql.jdbc.Driver", "jdbc:mysql://localhost:3306/person", "root", "dags");
			TableQuery tq = new TableQuery("person", pool);
			SQLContainer container = new SQLContainer(tq);
			addDetachListener(new DetachListener() {
				
				@Override
				public void detach(DetachEvent event) {
					pool.destroy();
					
				}
			});
			return container;
		}catch(SQLException e){
			throw new RuntimeException();
		}
		
	}
	
	@Override
	protected void init(VaadinRequest request) {
		VerticalLayout layout = new VerticalLayout();
		layout.setSizeUndefined();
		layout.setWidth("100%");
		layout.setMargin(true);
		setContent(layout);
		
		Label headerLabel = new Label("Member Register");
		headerLabel.addStyleName(Reindeer.LABEL_H1);
		layout.addComponent(headerLabel);
		
		HorizontalLayout content = new HorizontalLayout();
		content.setSizeFull();
		layout.addComponent(content);
		layout.setExpandRatio(content, 1);
		SQLContainer container = createContainer();
		Navigator navigator = new Navigator(this, content);
		navigator.addView("", new MemberListView(container));
		navigator.addView("MemberView", new MemberView(container));
		
		navigator.navigateTo("");
		
	}

}