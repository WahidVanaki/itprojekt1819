//package de.hdm.itprojekt.client.gui;
//
//import com.google.gwt.cell.client.AbstractCell;
//import com.google.gwt.cell.client.ValueUpdater;
//import com.google.gwt.dom.client.Element;
//import com.google.gwt.dom.client.EventTarget;
//import com.google.gwt.dom.client.NativeEvent;
//import com.google.gwt.safehtml.shared.SafeHtmlBuilder;
//import com.google.gwt.user.client.Window;
//
//import de.hdm.itprojekt.shared.bo.Textbeitrag;
//
//public class CellListTextbeitrag extends AbstractCell<Textbeitrag> {
//
//	public CellListTextbeitrag(){
//		super("click", "keydown");
//	
//	}
//	@Override
//	public void onBrowserEvent(com.google.gwt.cell.client.Cell.Context context,
//			Element parent, Textbeitrag value, NativeEvent event,
//			ValueUpdater<Textbeitrag> valueUpdater) {
//		
//		super.onBrowserEvent(context, parent, value, event, valueUpdater);
//		
//		
//		if("click".equals(event.getType())){
//			
//	        EventTarget eventTarget = event.getEventTarget();
//	        if (parent.getFirstChildElement().isOrHasChild(Element.as(eventTarget))) {
//	            doAction(value, valueUpdater);
//	            
//	        SafeHtmlBuilder sb = new SafeHtmlBuilder();
//			sb.appendHtmlConstant("<div contentEditable='false' unselectable='true'>"
//				+ value.getInhalt() + "</div>");
//	        }}
//	}
//		@Override
//		public void render(com.google.gwt.cell.client.Cell.Context context, Textbeitrag value, SafeHtmlBuilder sb) {
//			// TODO Auto-generated method stub
//			
//		if(value == null){
//			return;
//		}		
//		sb.appendHtmlConstant("<p></p>");
//		sb.appendEscaped(value.getErzeugungsdatum().toString());
//		sb.appendHtmlConstant("<p></p>");
//		sb.appendHtmlConstant("<div id = 'hallowahid' contentEditable='true' unselectable='false'>"
//				+ value.getInhalt() + "</div>");
//		}
//	
//	    @Override
//	    protected void onEnterKeyDown(Context context, Element parent, Textbeitrag value, NativeEvent event,
//	        ValueUpdater<Textbeitrag> valueUpdater) {
//	      doAction(value, valueUpdater);
//	    }
//
//    
//
//	private void doAction(Textbeitrag value, ValueUpdater<Textbeitrag> valueUpdater) {
//
//        Window.alert("You selected " + value.getInhalt());
//        
//
//        // Trigger a value updater. In this case, the value doesn't actually
//        // change, but we use a ValueUpdater to let the app know that a value
//        // was clicked.
//        valueUpdater.update(value);
//        Window.alert("" + valueUpdater.toString());
//	
//	}
//}