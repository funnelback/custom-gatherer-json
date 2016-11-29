import com.funnelback.common.*;
import com.funnelback.common.config.*;
import com.funnelback.common.io.store.*;
import com.funnelback.common.io.store.xml.*;
import com.funnelback.common.utils.*;
import java.net.URL;

//JSON imports
import org.json.*;
/****
// Downloads JSON files listed in collection.cfg.start.urls and converts them to XML
// Author: Peter Levan, Sep 2016
// Version: 0.2, Nov 2016
***/

// Read $SEARCH_HOME
def searchHome = Environment.getValidSearchHome().getCanonicalPath();

// Create a configuration object to read collection.cfg
def config = new NoOptionsConfig(new File(args[0]), args[1]);

// Create a Store instance to store gathered data
def store = new XmlStoreFactory(config).newStore();

// Open the XML store
store.open()
// Open the start URLs file
File file = new File(searchHome+File.separatorChar+"conf"+File.separatorChar+config.value("collection")+File.separatorChar+"collection.cfg.start.urls")

def line
// counter for update status checks
def i=0

file.withReader { reader ->
    while ((line = reader.readLine())!=null) {

	// Update the collection update status, and monitor for stop requests
        if ((i % 100) == 0)
        {
                // Check to see if the update has been stopped
                if (config.isUpdateStopped()) {
                        store.close()
                        throw new RuntimeException("Update stop requested by user.");
                }
                config.setProgressMessage("Processed "+i+" records");
        }


        println "Gathering JSON for "+line;

        // Fetch the JSON file and convert it to XML
        def jsonText = new URL(line).getText();
        //println "JSON: "+jsonText;
        JSONObject json = new JSONObject(jsonText)
        String xml = XML.toString(json)
        //println "XML:"+xml
        println "<?xml version=\"1.0\" encoding=\"utf-8\"?>\n<json>\n"+xml+"\n</json>"
        def xmlContent = XMLUtils.fromString("<?xml version=\"1.0\" encoding=\"utf-8\"?>\n<json>"+xml+"\n</json>")

        store.add(new XmlRecord(xmlContent, line))
	i++
    }
}
// close() required for the store to be flushed
store.close()
