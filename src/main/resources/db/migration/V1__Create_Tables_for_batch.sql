CREATE TABLE mes.batch ( 
	batch_sid            integer  NOT NULL ,
	batch_id             integer  NOT NULL ,
	batch_process_sid    integer  NOT NULL ,
	batch_source_sid     integer  NOT NULL ,
	batch_nbr            integer   ,
	load_file_id         integer   ,
	load_file_nm         varchar(100)   ,
	created_on           timestamp   ,
	created_by           varchar(50)   ,
	updated_by           varchar(50)   ,
	updated_on           timestamp   ,
	merchant_nbr         integer   ,
	terminal_id          varchar(20)   ,
	merchant_batch_dt    timestamp   ,
	transaction_cnt      numeric(50)   ,
	CONSTRAINT unq_batch_master_batch_nbr UNIQUE ( batch_nbr ) ,
	CONSTRAINT pk_batch_master_batch_sid PRIMARY KEY ( batch_sid )
 );

CREATE TABLE mes.batch_execution ( 
	batch_execution_sid  integer  NOT NULL ,
	execution_sid        integer  NOT NULL ,
	batch_sid            integer  NOT NULL ,
	created_by           varchar(50)   ,
	created_on           timestamp   ,
	updated_by           varchar(50)   ,
	updated_on           timestamp   ,
	CONSTRAINT unq_ref_batch_execution_execution_sid UNIQUE ( execution_sid ) ,
	CONSTRAINT pk_batch_execution_batch_execution_sid PRIMARY KEY ( batch_execution_sid )
 );

CREATE TABLE mes.batch_status ( 
	batch_status_sid     integer  NOT NULL ,
	status_sid           integer   ,
	batch_sid            integer   ,
	created_on           timestamp   ,
	created_by           varchar(50)   ,
	updated_on           timestamp   ,
	updated_by           varchar(50)   ,
	CONSTRAINT pk_batch_status_batch_status_sid PRIMARY KEY ( batch_status_sid )
 );

CREATE TABLE mes.execution_details ( 
	execution_sid        integer  NOT NULL ,
	execution_id         integer   ,
	stage_cd             varchar(20)   ,
	level_cd             integer   ,
	execution_ts         timestamp   ,
	created_by           varchar(50)   ,
	created_on           timestamp   ,
	updated_by           varchar(50)   ,
	updated_on           timestamp   ,
	CONSTRAINT pk_execution_details_execution_id PRIMARY KEY ( execution_sid )
 );

CREATE TABLE mes.ref_batch_process_code ( 
	batch_process_sid    integer  NOT NULL ,
	batch_process_cd     varchar(20)   ,
	batch_process_nm     varchar(50)   ,
	batch_process_desc   varchar(250)   ,
	created_by           varchar(50)   ,
	created_on           timestamp   ,
	updated_by           varchar(50)   ,
	updated_on           timestamp   ,
	CONSTRAINT pk_processes_batch_processes_id PRIMARY KEY ( batch_process_sid )
 );

CREATE TABLE mes.ref_batch_source_code ( 
	batch_source_sid     integer  NOT NULL ,
	batch_source_cd      varchar(20)   ,
	batch_source_nm      varchar(50)   ,
	batch_source_desc    varchar(250)   ,
	created_by           varchar(50)   ,
	created_on           timestamp   ,
	updated_by           varchar(50)   ,
	updated_on           timestamp   ,
	CONSTRAINT pk_ref_batch_source_codes_batch_source_id PRIMARY KEY ( batch_source_sid )
 );

CREATE TABLE mes.ref_status_code ( 
	status_sid           integer  NOT NULL ,
	status_cd            varchar(10)   ,
	created_on           timestamp   ,
	created_by           varchar(50)   ,
	updated_on           timestamp   ,
	updated_by           varchar(50)   ,
	CONSTRAINT pk_ref_batch_status_code_batch_status_sid PRIMARY KEY ( status_sid )
 );


ALTER TABLE mes.batch ADD CONSTRAINT fk_batch_ref_batch_source_code FOREIGN KEY ( batch_source_sid ) REFERENCES mes.ref_batch_source_code( batch_source_sid );

ALTER TABLE mes.batch ADD CONSTRAINT fk_batch_ref_batch_process_code FOREIGN KEY ( batch_process_sid ) REFERENCES mes.ref_batch_process_code( batch_process_sid );

ALTER TABLE mes.batch_execution ADD CONSTRAINT fk_ref_batch_execution_batch FOREIGN KEY ( batch_sid ) REFERENCES mes.batch( batch_sid );

ALTER TABLE mes.batch_execution ADD CONSTRAINT fk_ref_batch_execution_execution_details FOREIGN KEY ( execution_sid ) REFERENCES mes.execution_details( execution_sid );

ALTER TABLE mes.batch_status ADD CONSTRAINT fk_batch_status_batch FOREIGN KEY ( batch_sid ) REFERENCES mes.batch( batch_sid );

ALTER TABLE mes.batch_status ADD CONSTRAINT fk_batch_status_ref_status_code FOREIGN KEY ( status_sid ) REFERENCES mes.ref_status_code( status_sid );


COMMENT ON TABLE mes.batch_execution IS 'batch_processing_stage identifies the current stage of the batch such as process started, prcess about to end etc.';

COMMENT ON TABLE mes.batch_status IS 'batch status captures informtion if a batch is in NEW, PICKED, RE-RUN status.';

COMMENT ON TABLE mes.execution_details IS 'execution table captures execution stage and level information foe ex for fullfillment clearing stages could be READY_BATCH, READY_RECORDS, APPLY_RULES, APPLY_INTERCHANGE and PERSIST_RECORDS with level codes as 0, 1, 2 etc 0 indicates STAGE_ENTRY, 1 indicates STAGE_IN_PROGRESS and 2 indicates STAGE_COMPLETE.';

COMMENT ON TABLE mes.ref_batch_process_code IS 'batch procesing codes identify the type process such as settlement clearing, funding etc.';

COMMENT ON TABLE mes.ref_batch_source_code IS 'batch_source_code identifies the source of the batch such as tsys, payment gateway and trident.';












