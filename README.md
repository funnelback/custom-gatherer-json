Downloads JSON from URLs listed in collection.cfg.start.urls and stores them internally as XML.

Custom collection.cfg options: none

Note: you need to set the following collection.cfg option otherwise cache copies will not work:
store.record.type=XmlRecord

However you need to create a collection.cfg.start.urls file and list the URLs to download, 1 URL per line (same format as used by a web collection).
