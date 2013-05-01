# hbase-client

A Clojure library for interacting with HBase.

## Usage

    (:require [hbase.config]
              [hbase.table])
        
Create a config object with an optional map of overrides

    (def config
        (hbase.config/create))

    (def config
        (hbase.config/create additional-options))

Connect to a table, config argument is optional 

    (def table
        (hbase.table/connect "t1"))

    (def table
        (hbase.table/connect "t1" config))
    
Put records in a table, there is a 4 argument and 5 argument version
the 4 argument version inserts multiple column-name value pairs from a map
the 5 argument version inserts a single column-name value pair

    (hbase.table/put table "k1" "f2" {"c2" "zebra" "c3" "panda"})

    (hbase.table/put table "k1" "f2"  "c3" "panda")

Get the records back out, there are 3 forms
2 argument form: get a map of maps of column-families column-names and values associated with the rowkey
3 argument form: get a map of column-names and values associated with a rowkey and column-family
4 argument form: get a values associate with a rowkey column-family and column-name

    (hbase.table/get table "k1")

    (hbase.table/get table "k1" "f2")
    
    (hbase.table/get table "k1" "f2" "c2")
    
    
## License

Copyright © 2013 David Williams

Distributed under the Eclipse Public License, the same as Clojure.
