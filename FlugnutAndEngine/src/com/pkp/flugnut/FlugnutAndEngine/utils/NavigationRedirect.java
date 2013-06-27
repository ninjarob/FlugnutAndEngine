package com.pkp.flugnut.FlugnutAndEngine.utils;

import android.content.Context;
import com.pkp.flugnut.FlugnutAndEngine.GLGame;
import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;
import org.xmlpull.v1.XmlPullParserFactory;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.util.HashMap;
import java.util.Map;

/**
 * Created with IntelliJ IDEA.
 * User: cyrilthomas
 * Date: 6/26/13
 * Time: 4:44 PM
 * To change this template use File | Settings | File Templates.
 */
public class NavigationRedirect {

    public static NavigationRedirect instance;

    private Context applicationContext;
    private Map<String, String> navigationRedirect;
    private XmlPullParser xmlPullParser;

    private static final String namespace = null;

    private final String XML_FILE_LOCATION = "xml/navigation.xml";
    private final String XML_FILE_ENCODING = "UTF-8";
    private final String XML_NAVIGATION_ELEMENT = "navigation";
    private final String XML_REDIRECT_ELEMENT = "redirect";
    private final String XML_ID_ELEMENT = "id";
    private final String XML_FROM_ELEMENT = "from";
    private final String XML_TO_ELEMENT = "to";


    private NavigationRedirect(Context context) {
        this.applicationContext = context;
        this.navigationRedirect = new HashMap<String, String>(1);
        try {
            loadFileXml();
            parseXmlAndConstructRedirects();
        } catch (XmlPullParserException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static NavigationRedirect initInstance(Context context) {
        if (instance == null) {
            instance = new NavigationRedirect(context);
        }
        return instance;
    }

    public static NavigationRedirect getInstance() {
        if (instance == null) {
            throw new IllegalStateException("You need to instantiate NavigationRedirect");
        }
        return instance;
    }

    public String getNavigationToClassName(String id) {
        return instance.navigationRedirect.get(id);
    }

    public Object getObjectToNavigate(String id, GLGame game) {
        String className = instance.navigationRedirect.get(id);
        try {
            return (Class.forName(className).getConstructor(GLGame.class).newInstance(game));

        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        return null;
    }

    private void loadFileXml() throws XmlPullParserException, IOException {
        XmlPullParserFactory xmlPullParserFactory = XmlPullParserFactory.newInstance();
        xmlPullParserFactory.setNamespaceAware(true);

        xmlPullParser = xmlPullParserFactory.newPullParser();
        xmlPullParser.setInput(applicationContext.getAssets().open(XML_FILE_LOCATION), XML_FILE_ENCODING);

    }

    private void parseXmlAndConstructRedirects() throws XmlPullParserException, IOException {

        int eventType = xmlPullParser.getEventType();

        while (eventType != XmlPullParser.END_DOCUMENT) {

            if (eventType == XmlPullParser.START_TAG) {
                if (xmlPullParser.getName().equals(XML_NAVIGATION_ELEMENT)) {
                    readNavigation(xmlPullParser);
                }
            }
            eventType = xmlPullParser.next();
        }

    }

    private void readNavigation(XmlPullParser parser) throws XmlPullParserException, IOException {

        while (parser.next() != XmlPullParser.END_TAG) {
            if (parser.getEventType() != XmlPullParser.START_TAG) {
                continue;
            }
            String name = parser.getName();
            if (name.equals(XML_REDIRECT_ELEMENT)) {
                readRedirect(xmlPullParser);
            }
        }
    }

    private void readRedirect(XmlPullParser parser) throws XmlPullParserException, IOException {

        String id = null;
        String from = null;
        String to = null;
        while (parser.next() != XmlPullParser.END_TAG) {
            if (parser.getEventType() != XmlPullParser.START_TAG) {
                continue;
            }
            String name = parser.getName();
            if (name.equals(XML_ID_ELEMENT)) {
                id = readId(parser);
            } else if (name.equals(XML_FROM_ELEMENT)) {
                // Not sure what to do with the from param
                from = readFrom(parser);
            } else if (name.equals(XML_TO_ELEMENT)) {
                to = readTo(parser);
            }
        }

        //Add the entry to the map
        if (id != null && !id.equals(GameConstants.EMPTY_STRING) && to != null && !to.equals(GameConstants.EMPTY_STRING)) {
            navigationRedirect.put(id, to);
        }
    }

    // Processes title tags in the feed.
    private String readId(XmlPullParser parser) throws IOException, XmlPullParserException {
        parser.require(XmlPullParser.START_TAG, namespace, XML_ID_ELEMENT);
        String title = readText(parser);
        parser.require(XmlPullParser.END_TAG, namespace, XML_ID_ELEMENT);
        return title;
    }

    // Processes title tags in the feed.
    private String readFrom(XmlPullParser parser) throws IOException, XmlPullParserException {
        parser.require(XmlPullParser.START_TAG, namespace, XML_FROM_ELEMENT);
        String title = readText(parser);
        parser.require(XmlPullParser.END_TAG, namespace, XML_FROM_ELEMENT);
        return title;
    }

    // Processes title tags in the feed.
    private String readTo(XmlPullParser parser) throws IOException, XmlPullParserException {
        parser.require(XmlPullParser.START_TAG, namespace, XML_TO_ELEMENT);
        String title = readText(parser);
        parser.require(XmlPullParser.END_TAG, namespace, XML_TO_ELEMENT);
        return title;
    }

    // For the tags title and summary, extracts their text values.
    private String readText(XmlPullParser parser) throws IOException, XmlPullParserException {
        String result = GameConstants.EMPTY_STRING;
        if (parser.next() == XmlPullParser.TEXT) {
            result = parser.getText();
            parser.nextTag();
        }
        return result;
    }
}
