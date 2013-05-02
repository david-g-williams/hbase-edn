# hbase

A Clojure library for interacting with HBase. 

## Usage

    (:require [hbase.config]
              [hbase.schema]
              [hbase.table])
        
Create a config object.  Optionally takes a map of additional key value pairs.

    (def config (hbase.config/create))
    
    (def config (hbase.config/create additional-params))

Create a table

    (hbase.schema/create-table "t1" "f1" "f2" "f3" config)
    
Connect to a table

    (def table (hbase.table/connect "t1" config))
    
Insert records

The four argument version inserts multiple column-name value pairs from a map.  
The five argument version inserts a single column-name value pair.  

    (hbase.table/put table "k1" "f2" {"c2" "zebra" "c3" "panda"})
    
    (hbase.table/put table "k1" "f2"  "c3" "panda")
    
Retrieve records

The two argument form retrieves a map of maps of column-families, column-names and column-values values associated with the rowkey.  
The three argument form retreives a map of column-names and column-values associated with a rowkey and column-family.  
The four argument form retreives the column-value associated with a rowkey, column-family and column-name.  

    (hbase.table/get table "k1")

    (hbase.table/get table "k1" "f2")
    
    (hbase.table/get table "k1" "f2" "c2")
    
Scan a table

    (let [cursor (hbase.table/scan table "k1" "r2")]
        (loop [result nil]
            (let [result (cursor)]
                (do-something result)
                (if (not= nil result)
                    (recur nil)))))

## License

Copyright © 2013 David Williams

Distributed under the Eclipse Public License, the same as Clojure.
