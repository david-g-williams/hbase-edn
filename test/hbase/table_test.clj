(ns hbase.table-test
  (:require [clojure.test]
            [hbase.config]
			[hbase.table]))

(def config 
	(hbase.config/create))

(def table 
	(hbase.table/connect "t1" config))
 
(clojure.test/deftest connect
	(clojure.test/testing "HBase table create."
		(clojure.test/is 
			(= 
				(type table)  
				org.apache.hadoop.hbase.client.HTable))))

(hbase.table/put table "k1" "f2" {"c2" "zebra"})
(hbase.table/put table "k1" "f2"  "c3" "panda")

(comment
(clojure.test/deftest put
	(clojure.test/testing "Test put operation"
		(clojure.test/is)))
)
