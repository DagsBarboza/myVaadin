package com.example.myvaadinwithdatabase;

import com.vaadin.data.util.sqlcontainer.SQLContainer;
import com.vaadin.event.Action;
import com.vaadin.event.Action.Handler;
import com.vaadin.server.ExternalResource;
import com.vaadin.ui.Link;
import com.vaadin.ui.Table;

public class MyTable extends Table {
	
	private static final Action EDITACTION = new Action("Edit");
	private static final Action DELETEACTION = new Action("Delete");

	public MyTable(final SQLContainer container) {
		setContainerDataSource(container);
		setVisibleColumns(new String[]{"firstname", "lastname", "emailAdd"});
		
		addActionHandler(new Handler() {
			
			@Override
			public void handleAction(Action action, Object sender, Object target) {
				if (action == EDITACTION){
					getUI().getNavigator().navigateTo("MemberView/"+target);
				}else if (action == DELETEACTION){
					getUI().addWindow(new ConfirmationWindow(container, target ));
				}
				
			}
			
			@Override
			public Action[] getActions(Object target, Object sender) {
				// TODO Auto-generated method stub
				return new Action[]{ EDITACTION, DELETEACTION} ;
			}
		});
		
		addGeneratedColumn("emailAdd", new ColumnGenerator() {
			
			@Override
			public Object generateCell(Table source, Object itemId, Object columnId) {
				String email = (String) container.getContainerProperty(itemId, columnId).getValue();
				if (email==null || email.equals(""))
					return null;
				return new Link(email, new ExternalResource("mailto:"+email));
			}
		});
	}
}
