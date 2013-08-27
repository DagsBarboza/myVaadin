package com.example.myvaadinwithdatabase;

import com.vaadin.data.util.sqlcontainer.SQLContainer;
import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener.ViewChangeEvent;
import com.vaadin.ui.Button;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.Button.ClickListener;
import com.vaadin.ui.CustomComponent;
import com.vaadin.ui.Table;
import com.vaadin.ui.VerticalLayout;

public class MemberListView extends CustomComponent implements View {

	public MemberListView(SQLContainer container){
		
		setSizeFull();
		
		VerticalLayout layout = new VerticalLayout();
		layout.setSizeFull();
		setCompositionRoot(layout);
		
		MyTable table = new MyTable(container);
		table.setSizeFull();
		layout.addComponent(table);
		layout.setExpandRatio(table, 1);
		
		layout.addComponent(new Button("Add new", new ClickListener() {
			
			@Override
			public void buttonClick(ClickEvent event) {
				getUI().getNavigator().navigateTo("MemberView/new");
				
			}
		}));
	}
	
	@Override
	public void enter(ViewChangeEvent event) {
		// TODO Auto-generated method stub

	}

}
