package com.pkp.flugnut.FlugnutDimensions.utils;

import android.content.Context;
import com.pkp.flugnut.FlugnutDimensions.level.Level;
import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;
import org.xmlpull.v1.XmlPullParserFactory;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created with IntelliJ IDEA.
 * User: cyrilthomas
 * Date: 6/26/13
 * Time: 4:44 PM
 * To change this template use File | Settings | File Templates.
 */
public class LevelXmlParser {

    public static LevelXmlParser instance;

    private Context applicationContext;
    private Map<String, List<Level>> sceneLevelDetails;
    private XmlPullParser xmlPullParser;

    private static final String namespace = null;

    private final String XML_FILE_LOCATION = "xml/levels.xml";
    private final String XML_FILE_ENCODING = "UTF-8";
    private final String XML_SCENES_ELEMENT = "scenes";
    private final String XML_SCENE_ELEMENT = "scene";
    private final String XML_LEVELS_ELEMENT = "levels";
    private final String XML_LEVEL_ELEMENT = "level";
    private final String XML_ID_ELEMENT = "id";
    private final String XML_HAS_WEAPONS_ELEMENT = "hasWeapon";
    private final String XML_IS_UNLOCKED_ELEMENT = "isUnlocked";
    private final String XML_PRECONDITION_ELEMENT = "preCondition";
    private final String XML_GAME_SCENE_ELEMENT = "gameScene";


    private LevelXmlParser(Context context) {
        this.applicationContext = context;
        this.sceneLevelDetails = new HashMap<String, List<Level>>(1);
        try {
            loadFileXml();
            parseXmlAndConstructRedirects();
        } catch (XmlPullParserException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static LevelXmlParser initInstance(Context context) {
        if (instance == null) {
            instance = new LevelXmlParser(context);
        }
        return instance;
    }

    public static LevelXmlParser getInstance() {
        if (instance == null) {
            throw new IllegalStateException("You need to instantiate LevelXmlParser");
        }
        return instance;
    }

    public List<Level> getLevels(String id) {
        return instance.sceneLevelDetails.get(id);
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
                if (xmlPullParser.getName().equals(XML_SCENES_ELEMENT)) {
                    readScenes(xmlPullParser);
                }
            }
            eventType = xmlPullParser.next();
        }
    }

    private void readScenes(XmlPullParser parser) throws XmlPullParserException, IOException {

        while (parser.next() != XmlPullParser.END_TAG && parser.next() != XmlPullParser.END_DOCUMENT) {
            if (parser.getEventType() != XmlPullParser.START_TAG) {
                continue;
            }
            String name = parser.getName();

            if (name.equals(XML_SCENE_ELEMENT)) {
                readScene(xmlPullParser);
            }
        }
    }

    private void readScene(XmlPullParser parser) throws XmlPullParserException, IOException {

        String id = null;
        List<Level> levels = null;
        while (parser.next() != XmlPullParser.END_TAG) {
            if (parser.getEventType() != XmlPullParser.START_TAG) {
                continue;
            }
            String name = parser.getName();
            if (name.equals(XML_ID_ELEMENT)) {
                id = readId(parser);
                levels = readLevels(xmlPullParser);
            }
            sceneLevelDetails.put(id, levels);
        }
    }


    private List<Level> readLevels(XmlPullParser parser) throws XmlPullParserException, IOException {
        List<Level> levels = new ArrayList<Level>(1);
        while (parser.next() != XmlPullParser.END_TAG) {
            if (parser.getEventType() != XmlPullParser.START_TAG) {
                continue;
            }
            String name = parser.getName();
            if (name.equals(XML_LEVELS_ELEMENT)) {
                levels.addAll(readLevel(xmlPullParser));
            }
        }
        return levels;
    }

    private List<Level> readLevel(XmlPullParser parser) throws XmlPullParserException, IOException {

        List<Level> levels = new ArrayList<Level>(1);
        while (parser.next() != XmlPullParser.END_TAG) {
            if (parser.getEventType() != XmlPullParser.START_TAG) {
                continue;
            }
            String name = parser.getName();
            if (name.equals(XML_LEVEL_ELEMENT)) {
                levels.add(readLevelDetails(xmlPullParser));
            }
        }
        return levels;
    }

    private Level readLevelDetails(XmlPullParser parser) throws XmlPullParserException, IOException {

        String id = null;
        String hasWeapon = null;
        String isUnlocked = null;
        String preCondition = null;
        String gameScene = null;
        while (parser.next() != XmlPullParser.END_TAG) {
            if (parser.getEventType() != XmlPullParser.START_TAG) {
                continue;
            }
            String name = parser.getName();
            if (name.equals(XML_ID_ELEMENT)) {
                id = readId(parser);
            } else if (name.equals(XML_HAS_WEAPONS_ELEMENT)) {
                // Not sure what to do with the from param
                hasWeapon = readFrom(parser);
            } else if (name.equals(XML_IS_UNLOCKED_ELEMENT)) {
                isUnlocked = readTo(parser);
            } else if (name.equals(XML_GAME_SCENE_ELEMENT)) {
                gameScene = readGameScene(parser);
            } else if (name.equals(XML_PRECONDITION_ELEMENT)) {
                preCondition = readPreCondition(parser);
            }
        }
        return new Level(id, Boolean.parseBoolean(hasWeapon), Boolean.parseBoolean(isUnlocked), gameScene, preCondition);
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
        parser.require(XmlPullParser.START_TAG, namespace, XML_HAS_WEAPONS_ELEMENT);
        String title = readText(parser);
        parser.require(XmlPullParser.END_TAG, namespace, XML_HAS_WEAPONS_ELEMENT);
        return title;
    }

    // Processes title tags in the feed.
    private String readTo(XmlPullParser parser) throws IOException, XmlPullParserException {
        parser.require(XmlPullParser.START_TAG, namespace, XML_IS_UNLOCKED_ELEMENT);
        String title = readText(parser);
        parser.require(XmlPullParser.END_TAG, namespace, XML_IS_UNLOCKED_ELEMENT);
        return title;
    }

    // Processes title tags in the feed.
    private String readGameScene(XmlPullParser parser) throws IOException, XmlPullParserException {
        parser.require(XmlPullParser.START_TAG, namespace, XML_GAME_SCENE_ELEMENT);
        String title = readText(parser);
        parser.require(XmlPullParser.END_TAG, namespace, XML_GAME_SCENE_ELEMENT);
        return title;
    }

    // Processes title tags in the feed.
    private String readPreCondition(XmlPullParser parser) throws IOException, XmlPullParserException {
        parser.require(XmlPullParser.START_TAG, namespace, XML_PRECONDITION_ELEMENT);
        String title = readText(parser);
        parser.require(XmlPullParser.END_TAG, namespace, XML_PRECONDITION_ELEMENT);
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
