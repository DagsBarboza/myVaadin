package com.example.myvaadinwithdatabase;

import java.sql.SQLException;

import com.vaadin.data.Item;
import com.vaadin.data.fieldgroup.FieldGroup;
import com.vaadin.data.fieldgroup.FieldGroup.CommitException;
import com.vaadin.data.fieldgroup.PropertyId;
import com.vaadin.data.util.sqlcontainer.RowId;
import com.vaadin.data.util.sqlcontainer.SQLContainer;
import com.vaadin.data.validator.EmailValidator;
import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener.ViewChangeEvent;
import com.vaadin.ui.Button;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.Button.ClickListener;
import com.vaadin.ui.CustomComponent;
import com.vaadin.ui.FormLayout;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Notification;
import com.vaadin.ui.TextField;
import com.vaadin.ui.VerticalLayout;

public class MemberView extends CustomComponent implements View {

	@PropertyId("firstname")
	private TextField firstName;
	@PropertyId("lastname")
	private TextField lastName;
	@PropertyId("emailAdd")
	private TextField emailAdd;
	
	private final SQLContainer sqlcontainer;
	private FieldGroup fieldGroup;
	
	public MemberView(final SQLContainer sqlcontainer){
		this.sqlcontainer = sqlcontainer;
		FormLayout layout = new FormLayout();
		setCompositionRoot(layout);
		
		firstName = new TextField("First Name");
		firstName.setNullRepresentation("");
		layout.addComponent(firstName);
		lastName = new TextField("Last Name");
		lastName.setNullRepresentation("");
		layout.addComponent(lastName);
		emailAdd = new TextField("Email Address");
		emailAdd.setNullRepresentation("");
		emailAdd.addValidator(new EmailValidator("*"));
		layout.addComponent(emailAdd);
		
		HorizontalLayout buttonLayout = new HorizontalLayout();
		buttonLayout.addComponent(new Button("Save", new ClickListener() {
			
			@Override
			public void buttonClick(ClickEvent event) {
				try {
					fieldGroup.commit();
					sqlcontainer.commit();
					Notification.show("SAVE....");
					getUI().getNavigator().navigateTo("");
				} catch (CommitException e) {
					e.printStackTrace();
				}catch (SQLException e) {
				
				
				}
				
			}
		}));
		
		buttonLayout.addComponent(new Button("Cancel", new ClickListener() {
			
			@Override
			public void buttonClick(ClickEvent event) {
				fieldGroup.discard();
				
			}
		}));
		
		buttonLayout.addComponent(new Button("Close", new ClickListener() {
			
			@Override
			public void buttonClick(ClickEvent event) {
				getUI().getNavigator().navigateTo("");
				
			}
		}));
		
		layout.addComponent(buttonLayout);
		
		
	}
	
	@Override
	public void enter(ViewChangeEvent event) {
		Item item;
		if ("new".equals(event.getParameters())){
			Object itemId = sqlcontainer.addItem();
			item = sqlcontainer.getItem(itemId);
		}else{
			int id = Integer.parseInt(event.getParameters());
			
			item = sqlcontainer.getItem(new RowId(new Object[] {id}));

		}
		
		fieldGroup = new FieldGroup(item);
		fieldGroup.bindMemberFields(this);

	}

}

