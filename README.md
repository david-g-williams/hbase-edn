# hbase

2013-06-13
The behavior tests for this project are currently failing due to an un-handled runtime exception in the HBaseTestingUtility.

https://issues.apache.org/jira/browse/HBASE-8944

[![Build Status](https://travis-ci.org/mobiusinversion/hbase.png)](https://travis-ci.org/mobiusinversion/hbase)


A Clojure library for interacting with HBase. 

    :dependencies [[hbase "0.1.6"]]
    
## Usage

    (:require [hbase.config]
              [hbase.schema]
              [hbase.table])
        
#### Create a config 

Connects to ZooKeeper using hbase-site.xml

    (def config (hbase.config/new))

#### Create a table

    (hbase.schema/create-table "t1" "f1" "f2" "f3" config)
    
#### Connect to a table

Returns an HTable

    (hbase.table/new "t1" config)
    
#### Put

The three argument version inserts multiple column-family column-name value pairs from a map.  
The four argument version inserts multiple column-name value pairs from a map.  
The five argument version inserts a single column-name value pair.  

    (hbase.table/put table "k1" {"f1" {"c0" "racoon" "c1" "penguin"} "f2" {"c2" "zebra" "c3" "panda"}})
    
	(hbase.table/put table "k1" "f2" {"c2" "zebra" "c3" "panda"})
    
    (hbase.table/put table "k1" "f2"  "c3" "panda")
    
#### Get

The two argument form returns a map of maps of column-families, column-names and column-values values associated with the rowkey.  
The three argument form returns a map of column-names and column-values associated with a rowkey and column-family.  
The four argument form returns the column-value associated with a rowkey, column-family and column-name.  

    (hbase.table/get table "k1")

    (hbase.table/get table "k1" "f2")
    
    (hbase.table/get table "k1" "f2" "c2")
    
#### Scan

Scan returns a function representing an iterator over the scan results.  The iterator returns nil when exhausted.

##### All familes

    (let [cursor (hbase.table/scan table "k1" "kn")]
        (loop [result (cursor)]
            (when (not= nil result)
                (prn result)
                (recur (cursor)))))

##### Specified families

    (hbase.table/scan table "k1" "kn" ["f1" "f2"])
    
##### Specified columns

    (hbase.table/scan table "k1" "kn" 
        {"f1" ["c1" "c2"] 
         "f3" ["c5" "c7"]})
    
## License

Copyright © 2013 David Williams

Distributed under the Eclipse Public License, the same as Clojure.
