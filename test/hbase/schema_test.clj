(ns hbase.schema-test
  (:require [clojure.test]
            [hbase.config]
			[hbase.schema]))

(def config (hbase.config/create))

(hbase.schema/create-table "t3" "f1" "f2" "f3" config)

(comment
(clojure.test/deftest create-table-test
	(clojure.test/testing "Create a new hbase table."
		(clojure.test/is
			(=
				(type (hbase.config/create))
				org.apache.hadoop.conf.Configuration))))
)


