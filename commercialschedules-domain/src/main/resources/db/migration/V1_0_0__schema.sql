

create extension if not exists "uuid-ossp";

CREATE TABLE carrier (
    id uuid DEFAULT uuid_generate_v4() PRIMARY KEY,
    carrier_name varchar(100),
    smdg_code varchar(3) NULL,
    nmfta_code varchar(4) NULL
);

CREATE TABLE service (
    id uuid DEFAULT uuid_generate_v4() PRIMARY KEY,
    carrier_id uuid NULL REFERENCES carrier (id),
    carrier_service_code varchar(11),
    carrier_service_name varchar(50),
    universal_service_reference varchar(8) NULL CHECK (universal_service_reference ~ '^SR\d{5}[A-Z]$')
);
CREATE TABLE vessel (
    id uuid DEFAULT uuid_generate_v4() PRIMARY KEY,
    vessel_imo_number varchar(7) NULL UNIQUE,
    vessel_name varchar(35) NULL,
    vessel_flag char(2) NULL,
    vessel_call_sign varchar(10) NULL,
    vessel_operator_carrier_id uuid NULL REFERENCES carrier (id),
    is_dummy boolean NOT NULL default false
);

CREATE TABLE vessel_schedule (
    id uuid DEFAULT uuid_generate_v4() PRIMARY KEY,
    vessel_id uuid NOT NULL REFERENCES vessel (id),
    service_id uuid NOT NULL REFERENCES service (id),
    created_date_time timestamp with time zone NOT NULL DEFAULT now()
);
CREATE TABLE voyage (
    id uuid DEFAULT uuid_generate_v4() PRIMARY KEY,
    carrier_voyage_number varchar(50) NOT NULL,
    universal_voyage_reference varchar(5) NULL,
    service_id uuid NULL REFERENCES service (id) INITIALLY DEFERRED
);

CREATE TABLE un_location (
    un_location_code char(5) PRIMARY KEY,
    un_location_name varchar(100) NULL,
    location_code char(3) NULL
);

CREATE TABLE address (
    id uuid DEFAULT uuid_generate_v4() PRIMARY KEY,
    name varchar(100) NULL,
    street varchar(100) NULL,
    street_number varchar(50) NULL,
    floor varchar(50) NULL,
    postal_code varchar(10) NULL,
    city varchar(65) NULL,
    state_region varchar(65) NULL,
    country varchar(75) NULL
);
CREATE TABLE location (
    id uuid DEFAULT uuid_generate_v4() PRIMARY KEY,
    location_name varchar(100) NULL,
    latitude varchar(10) NULL,
    longitude varchar(11) NULL,
    un_location_code char(5) NULL REFERENCES un_location (un_location_code),
    address_id uuid NULL REFERENCES address (id),
    facility_id uuid NULL  -- REFERENCES facility (but there is a circular relation, so we add the FK later)
);

CREATE TABLE facility (
    id uuid DEFAULT uuid_generate_v4() PRIMARY KEY,
    facility_name varchar(100) NULL,
    un_location_code varchar(6) NULL,
    facility_bic_code varchar(4) NULL,-- suffix uniquely identifying the facility when prefixed with the UN Locode
    facility_smdg_code varchar(6) NULL, -- suffix uniquely identifying the facility when prefixed with the UN Locode
    location_id uuid REFERENCES location(id)
);

ALTER TABLE location
    ADD FOREIGN KEY (facility_id) REFERENCES facility (id);

CREATE TABLE port_call_status_type (
    port_call_status_type_code varchar(4) NOT NULL PRIMARY KEY,
    port_call_status_type_name varchar(30) NOT NULL,
    port_call_status_type_description varchar(250) NOT NULL
);
CREATE TABLE mode_of_transport (
    mode_of_transport_code varchar(3) PRIMARY KEY,
    mode_of_transport_name varchar(100) NULL,
    mode_of_transport_description varchar(250) NULL,
    dcsa_transport_type varchar(50) NULL UNIQUE
);

CREATE TABLE transport_call (
    id uuid DEFAULT uuid_generate_v4() PRIMARY KEY,
    transport_call_reference varchar(100) NOT NULL DEFAULT uuid_generate_v4(),
    transport_call_sequence_number integer,
    facility_id uuid NULL REFERENCES facility (id),
    facility_type_code varchar(4) NULL, -- Free text field used if the facility cannot be identified
    location_id uuid NULL REFERENCES location (id),
    mode_of_transport_code varchar(3) NULL REFERENCES mode_of_transport (mode_of_transport_code),
    vessel_id uuid NULL REFERENCES vessel(id),
    import_voyage_id uuid NULL, -- references on line 800
    export_voyage_id uuid NULL,
    port_visit_reference varchar(50) NULL
);


CREATE TABLE event (
    event_id uuid DEFAULT uuid_generate_v4() PRIMARY KEY,
    event_classifier_code varchar(3) NOT NULL,
    event_created_date_time timestamp with time zone DEFAULT now() NOT NULL,
    event_date_time timestamp with time zone NOT NULL
);

CREATE TABLE transport_event (
    transport_event_type_code varchar(4) NOT NULL,
    delay_reason_code varchar(3) NULL,
    change_remark varchar(250),
    transport_call_id uuid NULL REFERENCES transport_call(id)
) INHERITS (event);

ALTER TABLE transport_event ADD PRIMARY KEY (event_id);

CREATE TABLE place (
    id UUID PRIMARY KEY,
    facility_type_code VARCHAR(100),
    location_id UUID,
    date_time TIMESTAMP WITH TIME ZONE NOT NULL,
    FOREIGN KEY (location_id) REFERENCES location (id)
);

CREATE TABLE point_to_point_routing (
    id UUID DEFAULT uuid_generate_v4() PRIMARY KEY,
    transit_time INT NOT NULL,
    place_of_receipt_id UUID NOT NULL,
    place_of_delivery_id UUID NOT NULL,
    FOREIGN KEY (place_of_receipt_id) REFERENCES place (id),
    FOREIGN KEY (place_of_delivery_id) REFERENCES place (id)
);


CREATE TABLE leg (
    id UUID DEFAULT uuid_generate_v4() PRIMARY KEY,
    sequence_number INT NOT NULL,
    mode_of_transport VARCHAR(100),
    vessel_operator_smdg_liner_code VARCHAR(100),
    vessel_imo_number VARCHAR(100),
    vessel_name VARCHAR(100),
    carrier_service_name VARCHAR(100),
    universal_service_reference VARCHAR(100),
    carrier_service_code VARCHAR(100),
    universal_import_voyage_reference VARCHAR(100),
    universal_export_voyage_reference VARCHAR(100),
    carrier_import_voyage_number VARCHAR(100),
    carrier_export_voyage_number VARCHAR(100),
    departure_id UUID,
    arrival_id UUID,
    point_to_point_routing_id UUID,
    FOREIGN KEY (departure_id) REFERENCES place (id),
    FOREIGN KEY (arrival_id) REFERENCES place (id),
    FOREIGN KEY (point_to_point_routing_id) REFERENCES point_to_point_routing (id)
);
INSERT INTO address (
    id,
    name,
    street,
    street_number,
    floor,
    postal_code,
    city,
    state_region,
    country
) VALUES (uuid('8791f557-fe69-42c9-a420-f39f09dd6207'),'Henrik','Kronprincessegade','54','5. sal','1306','København','N/A','Denmark'),
(uuid('8561f557-fe69-42c9-a420-f39f09dd6207'),'','C. Cam. del Puerto','1','','28821','Madrid','','Spain');
INSERT INTO un_location(
  un_location_code,
  un_location_name,
  location_code) VALUES('USNYC','New York','NYC'),
  ('SGSIN','Singapore','SIN'),
  ('JPTYO','Tokyo','TYO'),
  ('BRRIO','Rio de Janeiro','RIO'),
  ('NLRTM','Rotterdam','RTM'),
  ('FRPAR','Paris','PAR'),
  ('FRLEH','Le Harve','LEH'),
  ('NLAMS','Amsterdam','AMS');

INSERT INTO location (
    id,
    location_name,
    address_id,
    latitude,
    longitude,
    un_location_code,
    facility_id
) VALUES ('06aca2f6-f1d0-48f8-ba46-9a3480adfd23','Eiffel Tower',uuid('8791f557-fe69-42c9-a420-f39f09dd6207'),'48.8585500','2.294492036','USNYC',null),
  ('6748a259-fb7e-4f27-9a88-3669e8b9c5f8','Eiffel Tower 2', uuid('8791f557-fe69-42c9-a420-f39f09dd6207'),'48.8585500','2.294492036','SGSIN',null ),
  ('6748b859-fb7e-4f27-9a88-366978b9c5f8','Port of Rotterdam',null,null,null,'NLRTM',null),
  ('6748a248-fb7e-4f27-9a88-366978b9c5f8','Madrid Dry Port', uuid('8561f557-fe69-42c9-a420-f39f09dd6207'),null,null,null,null),
  ('dbbcec36-edb3-403b-870f-85abee25cac9', 'Tokyo',null,'35.6762','139.6503','JPTYO',null),
  ('daa0a384-51bb-4704-bada-3bb2443f03eb','Rio de Janeiro',null,'22.9068','43.1729','BRRIO',(SELECT id FROM facility WHERE un_location_code = 'BRSSZ' AND facility_smdg_code = 'BTP')),
  ('6748a259-fb7e-4f27-9a88-366978b9c5f8','Port of Amsterdam', null,null,null,'NLAMS',null),
  ('9048a259-fb7e-4f27-9a88-366978b9c5f8','', null,null,null,'FRPAR',null),
  ('8048a259-fb7e-4f27-9a88-366978b9c5f8','Le Havre', null,null,null,'FRLEH',null),
  ('1048a259-fb7e-4f27-9a88-366978b9c5f8','PSA Singapore Terminal', null,null,null,'SGSIN',null);

INSERT INTO facility(
  facility_name,
  un_location_code,
  facility_bic_code,
  facility_smdg_code,
  location_id)
  VALUES('BRASIL TERMINAL PORTUARIOS (BTP)','BRSSZ','','BTP','daa0a384-51bb-4704-bada-3bb2443f03eb'),
  ('APM TERMINALS ELIZABETH','USNYC','','APMT','06aca2f6-f1d0-48f8-ba46-9a3480adfd23'),
  ('BRANI TERMINAL','SGSIN','','PSABT','6748a259-fb7e-4f27-9a88-3669e8b9c5f8'),
  ('TERMINAL DE NORMANDIE MSC (TNMSC)','FRLEH','','TNMSC','8048a259-fb7e-4f27-9a88-366978b9c5f8'),
  ('','','','TNMSC','8048a259-fb7e-4f27-9a88-366978b9c5f8');


INSERT INTO carrier(
  carrier_name,
  smdg_code,
  nmfta_code
) VALUES
  ('CMA CGM','CMA','CMDU'),
  ('Evergreen Marine Corporation','EMC','EGLV'),
  ('Hapag Lloyd','HLC','HLCU'),
  ('Hyundai','HMM','HDMU'),
  ('Maersk','MSK','MAEU'),
  ('Mediterranean Shipping Company','MSC','MSCU'),
  ('Ocean Network Express Pte. Ltd.','ONE','ONEY'),
  ('Yang Ming Line','YML','YMLU'),
  ('Zim Israel Navigation Company','ZIM','ZIMU');

INSERT INTO vessel (
    vessel_imo_number,
    vessel_name,
    vessel_flag,
    vessel_call_sign,
    vessel_operator_carrier_id,
    is_dummy
) VALUES (
    '9136307',
    'King of the Seas',
    'US',
    'WDK4473',
    (SELECT id FROM carrier WHERE smdg_code = 'ONE'),false
),( '9811000',
    'Ever Given',
    'PA',
    'H3RC',
    (SELECT id FROM carrier WHERE smdg_code = 'EMC'),false
  );

INSERT INTO service (
    id,
    carrier_id,
    carrier_service_code,
    carrier_service_name,
    universal_service_reference
) VALUES (
     '03482296-ef9c-11eb-9a03-0242ac131999',
     (SELECT id FROM carrier WHERE smdg_code = 'MSK'),
     'A_CSC',
     'A_carrier_service_name',
     'SR00001D'
);

INSERT INTO service (
    id,
    carrier_id,
    carrier_service_code,
    carrier_service_name,
    universal_service_reference
) VALUES (
     'f65022f1-76e7-4cf2-8287-241cd7aed4de',
     (SELECT id FROM carrier WHERE smdg_code = 'HLC'),
     'B_HLC',
     'B_carrier_service_name',
     'SR00002B'
);

INSERT INTO service (
    id,
    carrier_id,
    carrier_service_code,
    carrier_service_name,
    universal_service_reference
) VALUES (
     'f26ac90d-c89a-4bff-9fd3-35c134a3ec31',
     (SELECT id FROM carrier WHERE smdg_code = 'HLC'),
     'B_HLC',
     'B_carrier_service_name_1',
     'SR00003H'
);

INSERT INTO voyage (
    id,
    carrier_voyage_number,
    service_id
) VALUES (
     '03482296-ef9c-11eb-9a03-0242ac131233',
     'A_carrier_voyage_number',
     '03482296-ef9c-11eb-9a03-0242ac131999'
);

INSERT INTO voyage (
    id,
    carrier_voyage_number,
    universal_voyage_reference,
    service_id
) VALUES (
     '3fb0b919-f38c-4198-b61f-b08c361858f7',
     '4419W',
     '657AS',
     'f65022f1-76e7-4cf2-8287-241cd7aed4de'
);

INSERT INTO voyage (
    id,
    carrier_voyage_number,
    universal_voyage_reference,
    service_id
) VALUES (
     '6f034c96-97e4-47c6-a140-c44e9be50609',
     '4420E',
     '657AN',
     'f65022f1-76e7-4cf2-8287-241cd7aed4de'
);
INSERT INTO transport_call (
    id,
    transport_call_reference,
    transport_call_sequence_number,
    facility_id,
    facility_type_code,
    vessel_id,
    import_voyage_id,
    export_voyage_id,
    location_id,
    port_visit_reference
) VALUES (
    '7f2d833c-2c7f-4fc5-a71a-e510881da64a'::uuid,
    'TC-REF-08_03-A',
    1,
    (SELECT id FROM facility WHERE un_location_code = 'USNYC' AND facility_smdg_code = 'APMT'),
    'POTE',
    (SELECT id FROM vessel WHERE vessel_imo_number = '9811000'),
    uuid('03482296-ef9c-11eb-9a03-0242ac131233'),
    uuid('03482296-ef9c-11eb-9a03-0242ac131233'),
    uuid('06aca2f6-f1d0-48f8-ba46-9a3480adfd23'),
    'NLRTM1234589'
), (
    'b785317a-2340-4db7-8fb3-c8dfb1edfa60'::uuid,
    'TC-REF-08_03-B',
    2,
    (SELECT id FROM facility WHERE un_location_code = 'BRSSZ' AND facility_smdg_code = 'BTP'),
    'POTE',
    (SELECT id FROM vessel WHERE vessel_imo_number = '9811000'),
    (SELECT id FROM voyage WHERE carrier_voyage_number = 'A_carrier_voyage_number'),
    (SELECT id FROM voyage WHERE carrier_voyage_number = 'A_carrier_voyage_number'),
    uuid('daa0a384-51bb-4704-bada-3bb2443f03eb'),
    null
), (
    '85ae996d-a598-4ebe-acfe-45d8a2eb7039'::uuid,
    'TC-REF-08_06-B',
    2,
    (SELECT id FROM facility WHERE un_location_code = 'SGSIN' AND facility_smdg_code = 'PSABT'),
    'POTE',
    (SELECT id FROM vessel WHERE vessel_imo_number = '9811000'),
    (SELECT id FROM voyage WHERE carrier_voyage_number = '4419W'),
    (SELECT id FROM voyage WHERE carrier_voyage_number = '4420E'),
    uuid('dbbcec36-edb3-403b-870f-85abee25cac9'),
    null
);

INSERT INTO vessel_schedule(
    vessel_id,
    service_id
) VALUES (
    (SELECT id FROM vessel WHERE vessel_imo_number = '9811000'),
    (SELECT id FROM service WHERE universal_service_reference = 'SR00002B')
);

INSERT INTO vessel_schedule(
    vessel_id,
    service_id
) VALUES (
    (SELECT id FROM vessel WHERE vessel_imo_number = '9136307'),
    (SELECT id FROM service WHERE universal_service_reference = 'SR00003H')
);

INSERT INTO place(
  id,
  facility_type_code,
  location_id,
  date_time
  ) VALUES(uuid('8948a259-fb7e-4f27-9a88-366978b9c5f8'),'POTE','6748a259-fb7e-4f27-9a88-366978b9c5f8','2019-08-24T14:15:22Z'),
  (uuid('4548a259-fb7e-4f27-9a88-366978b9c5f8'),'COYA','6748b859-fb7e-4f27-9a88-366978b9c5f8','2023-12-4T07:00+01:00'),
  (uuid('5490a259-fb7e-4f27-9a88-366978b9c5f8'),'COYA','6748a259-fb7e-4f27-9a88-366978b9c5f8','2023-12-12T20:00+01:00'),
  (uuid('9090a259-fb7e-4f27-9a88-366978b9c5f8'),'WAYP','9048a259-fb7e-4f27-9a88-366978b9c5f8','2024-01-14T13:00+01:00'),
  (uuid('8090a259-fb7e-4f27-9a88-366978b9c5f8'),'POTE','8048a259-fb7e-4f27-9a88-366978b9c5f8','2024-01-17T14:00+01:00'),
  (uuid('1090a259-fb7e-4f27-9a88-366978b9c5f8'),'POTE','8048a259-fb7e-4f27-9a88-366978b9c5f8','2024-01-22T14:00+01:00'),
  (uuid('1190a259-fb7e-4f27-9a88-366978b9c5f8'),'POTE','1048a259-fb7e-4f27-9a88-366978b9c5f8','2024-02-10T14:28+08:00'),
  (uuid('7020a259-fb7e-4f27-9a88-366978b9c5f8'),'WAYP','9048a259-fb7e-4f27-9a88-366978b9c5f8','2024-01-14T13:00+01:00'),
  (uuid('8020a259-fb7e-4f27-9a88-366978b9c5f8'),'CLOC','1048a259-fb7e-4f27-9a88-366978b9c5f8','2024-02-12T12:28+08:00');
INSERT INTO point_to_point_routing(
  id,
  transit_time,
  place_of_receipt_id,
  place_of_delivery_id
  ) VALUES ('4548a259-ae7e-4f27-9a88-366978b9c5f8',10,'4548a259-fb7e-4f27-9a88-366978b9c5f8','5490a259-fb7e-4f27-9a88-366978b9c5f8'),
  ('9988a259-ae7e-4f27-9a88-366978b9c5f8',20,'7020a259-fb7e-4f27-9a88-366978b9c5f8','8020a259-fb7e-4f27-9a88-366978b9c5f8');

INSERT INTO leg(
    sequence_number,
    mode_of_transport,
    vessel_operator_smdg_liner_code,
    vessel_imo_number,
    vessel_name,
    carrier_service_name,
    universal_service_reference,
    carrier_service_code,
    universal_import_voyage_reference,
    universal_export_voyage_reference,
    carrier_import_voyage_number,
    carrier_export_voyage_number,
    departure_id,
    arrival_id,
    point_to_point_routing_id
)VALUES(1,'VESSEL','HLC','9321483','King of the Seas','Great Lion Service','SR12345A','FE1','2103N','2103N','2103S','2103N','4548a259-fb7e-4f27-9a88-366978b9c5f8','5490a259-fb7e-4f27-9a88-366978b9c5f8','4548a259-ae7e-4f27-9a88-366978b9c5f8'),
       (1,'BARGE','','','','','','','','','','','9090a259-fb7e-4f27-9a88-366978b9c5f8','8090a259-fb7e-4f27-9a88-366978b9c5f8','9988a259-ae7e-4f27-9a88-366978b9c5f8'),
       (2,'VESSEL','MSC','9930038','MSC TESSA','Singapore Express','SR12365B','AE55','2401E','2401E','401E','401E','1090a259-fb7e-4f27-9a88-366978b9c5f8','1190a259-fb7e-4f27-9a88-366978b9c5f8','9988a259-ae7e-4f27-9a88-366978b9c5f8');






