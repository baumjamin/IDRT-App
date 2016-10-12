#!/bin/sh
cd `dirname $0`
ROOT_PATH=`pwd`
java -Xms512M -Xmx2048M -cp .:$ROOT_PATH:$ROOT_PATH/../lib/routines.jar:$ROOT_PATH/../lib/commons-collections-3.2.2.jar:$ROOT_PATH/../lib/log4j-1.2.15.jar:$ROOT_PATH/../lib/log4j-1.2.16.jar:$ROOT_PATH/../lib/xercesImpl.jar:$ROOT_PATH/../lib/dom4j-1.6.1.jar:$ROOT_PATH/../lib/postgresql-9.3-1102.jdbc4.jar:$ROOT_PATH/../lib/ojdbc14.jar:$ROOT_PATH/../lib/OpenCSV.jar:$ROOT_PATH/../lib/StatusListener29.jar:$ROOT_PATH/../lib/TalendSAX.jar:$ROOT_PATH/../lib/jaxen-1.1.1.jar:$ROOT_PATH/../lib/advancedPersistentLookupLib-1.0.jar:$ROOT_PATH/../lib/jboss-serialization.jar:$ROOT_PATH/../lib/postgresql-8.4-703.jdbc4.jar:$ROOT_PATH/../lib/talendcsv.jar:$ROOT_PATH/../lib/saxon9.jar:$ROOT_PATH/../lib/trove.jar:$ROOT_PATH/../lib/jakarta-oro-2.0.8.jar:$ROOT_PATH/../lib/talend_file_enhanced_20070724.jar:$ROOT_PATH/../lib/xpathutil-1.0.0.jar:$ROOT_PATH/../lib/ojdbc6.jar:$ROOT_PATH/../lib/postgresql-9.2-1003.jdbc3.jar:$ROOT_PATH/odm_master_0_1.jar:$ROOT_PATH/odm_extract_pd_0_1.jar:$ROOT_PATH/database_drop_indexing_0_1.jar:$ROOT_PATH/idrt_jdbc_upload_transmart_1_0.jar:$ROOT_PATH/odm_create_ontology_1_1.jar:$ROOT_PATH/database_stop_indexing_0_1.jar:$ROOT_PATH/idrt_to_db_schema_1_0.jar:$ROOT_PATH/idrt_jdbc_upload_1_0.jar:$ROOT_PATH/odm_extract_pd_with_xslt_0_1.jar:$ROOT_PATH/idrt_set_patient_count_0_1.jar:$ROOT_PATH/database_alter_tables_0_1.jar:$ROOT_PATH/idrt_truncate_tables_0_1.jar:$ROOT_PATH/database_rebuild_indexing_0_1.jar:$ROOT_PATH/database_create_indexing_0_1.jar: i2b2transmart.odm_master_0_1.ODM_MASTER --context=Default "$@" 