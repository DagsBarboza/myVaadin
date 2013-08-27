package com.example.myvaadinwithdatabase;

import java.sql.SQLException;

import com.vaadin.data.util.sqlcontainer.SQLContainer;
import com.vaadin.ui.Button;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.Button.ClickListener;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Label;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.Window;

public class ConfirmationWindow extends Window {

	public ConfirmationWindow(final SQLContainer container, final Object itemId) {
		setCaption("Delete confirmation");
		setModal(true);
		setResizable(false);
		
		VerticalLayout layout = new VerticalLayout();
		HorizontalLayout buttonLayout = new HorizontalLayout();
		
		layout.addComponent(new Label("Are your sure you want to delete"));
		layout.addComponent(buttonLayout);
		
		setContent(layout);
		
		buttonLayout.addComponent(new Button("Yes", new ClickListener() {

			@Override
			public void buttonClick(ClickEvent event) {
				try {
					container.removeItem(itemId);
					container.commit();
					getUI().removeWindow(ConfirmationWindow.this);
				} catch (SQLException e) {

				}

			}
		}));

		buttonLayout.addComponent(new Button("No", new ClickListener() {

			@Override
			public void buttonClick(ClickEvent event) {
				getUI().removeWindow(ConfirmationWindow.this);

			}
		}));
	}
}
