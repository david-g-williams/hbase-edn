(ns hbase.schema-test
  (:require [clojure.test]
            [hbase.config]
			[hbase.schema]))

(def config (hbase.config/create))

(hbase.schema/create-table "t3" "f1" "f2" "f3" config)



