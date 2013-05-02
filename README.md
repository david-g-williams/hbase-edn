# hbase

A Clojure library for interacting with HBase. 

## Usage

    (:require [hbase.config]
              [hbase.schema]
              [hbase.table])
        
#### Create a config 

This connects to your ZooKeeper

    (def config (hbase.config/create))

#### Create a table

    (hbase.schema/create-table "t1" "f1" "f2" "f3" config)
    
#### Connect to a table

    (def table (hbase.table/connect "t1" config))
    
#### Put

The four argument version inserts multiple column-name value pairs from a map.  
The five argument version inserts a single column-name value pair.  

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

    (let [cursor (hbase.table/scan table "k1" "kn")]
        (loop [result (cursor)]
            (when (not= nil result)
                (prn result)
                (recur (cursor)))))

## License

Copyright © 2013 David Williams

Distributed under the Eclipse Public License, the same as Clojure.
