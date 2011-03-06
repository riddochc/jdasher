package dasher.utils;

import dasher.resources.StaticResourceManager;
import java.awt.Component;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Stack;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.AbstractButton;
import javax.swing.ButtonGroup;
import javax.swing.JCheckBoxMenuItem;
import javax.swing.JComponent;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JRadioButtonMenuItem;
import javax.swing.JSeparator;
import org.xml.sax.Attributes;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

/**
 * This class is resposible for generating menu bars from XML
 * that conforms to <code>menubar.dtd</code>.
 *
 * @author joshua
 */
public class MenuReader extends ConvenientXMLParser<JComponent> {

    protected Object receiver;
    protected boolean byAction = false;
    protected Stack<JComponent> stack = new Stack<JComponent>();
    protected Stack<StringBuilder> bufs = new Stack<StringBuilder>();
    protected boolean curHasMethod = false;
    protected final Class[] emptyArrayOfClasses = {};
    protected final Object[] emptyArrayOfObjects = {};

    public MenuReader() throws SAXException {
        super();
        dtd = "menubar.dtd";
    }

//    /**
//     * Parses a XML InputSource for menu data into a JComponent which can be retreived
//     * later by <code>getResult</code>.
//     *
//     * @param in the InputSource from which to read the XML menu
//     * @param receiver either an object that can receive the methods in the XML
//     *              or a ActionListener
//     * @param byAction whether to treat the receiver as an ActionListener
//     * @throws IOException
//     * @throws SAXException
//     * @see #getResult()
//     */
//    public void parse(InputSource in, Object receiver, boolean byAction) throws IOException, SAXException {
//        this.receiver = receiver;
//        this.byAction = byAction;
//        reader.parse(in);
//    }
    /**
     * Parses a XML InputSource for menu data into a JComponent.
     *
     * @param in the InputSource from which to read the XML menu
     * @param receiver either an object that can receive the methods in the XML
     *              or a ActionListener
     * @param byAction whether to treat the receiver as an ActionListener
     * @return the resuling JComponent
     * @throws IOException
     * @throws SAXException
     */
    public JComponent parse(InputSource in, Object receiver, boolean byAction) throws IOException, SAXException {
        this.receiver = receiver;
        this.byAction = byAction;
        return parse(in);
    }

    /**
     * Parses a XML InputStream for menu data into a JComponent.
     *
     * @param in the InputStream from which to read the XML menu
     * @param receiver either an object that can receive the methods in the XML
     *              or a ActionListener
     * @param byAction whether to treat the receiver as an ActionListener
     * @return the resuling JComponent
     * @throws IOException
     * @throws SAXException
     */
    public JComponent parse(InputStream in, Object receiver, boolean byAction) throws IOException, SAXException {
        return parse(new InputSource(in), receiver, byAction);
    }

    /**
     * Parses a XML file for menu data into a JComponent.
     *
     * @param url the URL from which to read the XML menu
     * @param receiver either an object that can receive the methods in the XML
     *              or a ActionListener
     * @param byAction whether to treat the receiver as an ActionListener
     * @return the resuling JComponent
     * @throws IOException
     * @throws SAXException
     */
    public JComponent parse(String url, Object receiver, boolean byAction) throws IOException, SAXException {
        return parse(new InputSource(url), receiver, byAction);
    }

    public JComponent getResult() {
        return result;
    }

    protected InputStream getDTDEntity() throws IOException {
        return StaticResourceManager.getResourceStream("filelist.dtd");
    }

    @Override
    public void startElement(String namespaceURI, String simpleName, String qualName, Attributes tagAttributes) throws SAXException {
        String tagName = (simpleName.equals("") ? qualName : simpleName);
        JComponent current = null;
        AbstractButton item = null;
        HashMap<String, String> atts = new HashMap<String, String>();
        for (int i = 0; i < tagAttributes.getLength(); i++) {
            String attributeName = (tagAttributes.getLocalName(i).equals("") ? tagAttributes.getQName(i) : tagAttributes.getLocalName(i));
            atts.put(attributeName, tagAttributes.getValue(i));
        }
        curHasMethod = false;
        String name = atts.get("name"),
                enabled = atts.get("enabled"),
                method = atts.get("method"),
                shortcut = atts.get("shortcut"),
                value = atts.get("value");
        if (tagName.equals("menubar")) {
            current = new JMenuBar();
            if (atts.get("byaction") != null)
                byAction = atts.get("byaction").equals("true");
        } else if (tagName.equals("menu")) {
            item = new JMenu(name);
        } else if (tagName.equals("item")) {
            item = new JMenuItem();
        } else if (tagName.equals("checkitem")) {
            item = new JCheckBoxMenuItem();
            item.setSelected(value.equals("true"));
        } else if (tagName.equals("choicemenu")) {
            item = new ChoiceMenu();
        } else if (tagName.equals("choice")) {
            item = new JRadioButtonMenuItem();
            item.setSelected(value.equals("true"));
        } else if (tagName.equals("separator")) {
            current = new JSeparator();
        } else {
            return;
        }
        if (item != null) {
            if (enabled != null)
                item.setEnabled(enabled.equals("true"));
            if (shortcut != null) {
                try {
                    item.setMnemonic(KeyEvent.class.getField("VK_" + shortcut.toUpperCase()).getInt(KeyEvent.class));
                } catch (IllegalArgumentException ex) {
                    Logger.getLogger(MenuReader.class.getName()).log(Level.SEVERE, null, ex);
                } catch (IllegalAccessException ex) {
                    Logger.getLogger(MenuReader.class.getName()).log(Level.SEVERE, null, ex);
                } catch (NoSuchFieldException ex) {
                    Logger.getLogger(MenuReader.class.getName()).log(Level.SEVERE, null, ex);
                } catch (SecurityException ex) {
                    Logger.getLogger(MenuReader.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
            if (name != null)
                item.setText(name);
            if (method != null) {
                curHasMethod = true;
                if (tagName.equals("item") || tagName.equals("menu") || tagName.equals("choice"))
                    addActionMethod(item, method);
                if (tagName.equals("checkitem"))
                    addCheckMethod(item, method);
                if (tagName.equals("choicemenu"))
                    addChoiceMethod(item, method);
            }
            current = item;
        }
        bufs.push(new StringBuilder());
        stack.push(current);
    }
    
    @Override
    public void characters(char[] ch,
                       int start,
                       int length) throws SAXException {
        if (bufs.empty())
            bufs.push(new StringBuilder(length));
        bufs.peek().append(ch, start, length);
    }

    @Override
    public void endElement(String uri,
                       String localName,
                       String qName) throws SAXException {
        String tagName = (localName.equals("") ? qName : localName);
        JComponent current = stack.pop();
        StringBuilder buf = bufs.pop();
        if (tagName.equals("item") || tagName.equals("checkitem") || tagName.equals("choice"))
            if (buf.length() > 0)
                ((JMenuItem) current).setText(buf.toString());
        if (!curHasMethod) {
            if (tagName.equals("item"))
                addActionMethod(current, "menu" + ((JMenuItem) current).getText().replaceAll("\\W", ""));
            if (tagName.equals("checkitem"))
                    addCheckMethod(current, "menu" + ((JMenuItem) current).getText().replaceAll("\\W", ""));
            if (tagName.equals("choicemenu"))
                addChoiceMethod(current, "menu" + ((JMenuItem) current).getText().replaceAll("\\W", ""));
        }
        if (stack.empty())
            result = current;
        else
            stack.peek().add(current);
    }

    private void addActionMethod(JComponent item, final String text) {
        if (!byAction) {
            try {
                final Method method = receiver.getClass().getMethod(text, emptyArrayOfClasses);
                ((JMenuItem) item).addActionListener(new ActionListener() {

                    public void actionPerformed(ActionEvent e) {
                        try {
                            method.invoke(receiver, emptyArrayOfObjects);
                        } catch (IllegalAccessException ex) {
                            Logger.getLogger(MenuReader.class.getName()).log(Level.SEVERE, text, ex);
                        } catch (IllegalArgumentException ex) {
                            Logger.getLogger(MenuReader.class.getName()).log(Level.SEVERE, text, ex);
                        } catch (InvocationTargetException ex) {
                            Logger.getLogger(MenuReader.class.getName()).log(Level.SEVERE, text, ex);
                        }
                    }
                });
            } catch (NoSuchMethodException ex) {
                Logger.getLogger(MenuReader.class.getName()).log(Level.SEVERE, text, ex);
            } catch (SecurityException ex) {
                Logger.getLogger(MenuReader.class.getName()).log(Level.SEVERE, text, ex);
            }
        } else {
            ((JMenuItem) item).addActionListener((ActionListener) receiver);
        }
    }

    private void addCheckMethod(JComponent item, final String text) {
        // TODO: addCheckMethod
        ((JMenuItem) item).addActionListener(
                new ActionListener() {
                    public void actionPerformed(ActionEvent e) {
                        throw new UnsupportedOperationException("Not supported yet: " + text);
                    }
        });
    }

    private void addChoiceMethod(JComponent item, final String text) {
        // TODO: addChoiceMethod
        ((JMenuItem) item).addActionListener(
                new ActionListener() {
                    public void actionPerformed(ActionEvent e) {
                        throw new UnsupportedOperationException("Not supported yet: " + text);
                    }
        });
    }

    class ChoiceMenu extends JMenu {
        protected ButtonGroup group = new ButtonGroup();

        @Override
        public Component add(Component comp) {
            group.add((AbstractButton) comp);
            return super.add(comp);
        }

    }
}
