# JSON custom gatherer

Note: if you are using Funnelback 15.10 or newer you should use the built-in JSONToXML filter with a web collection instead of using this custom gatherer.

Custom gatherer for use with a custom collection for download of json files.

Add URLs for JSON to fetch to collection.cfg.start.urls file.  Format is 1 URL per line.  Each URL is fetched and the JSON is converted to XML.

Note: you need to set the following collection.cfg option otherwise cache copies will not work:

```
store.record.type=XmlRecord
```
