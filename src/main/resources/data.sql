-- choose spring schema
USE spring;

INSERT INTO country (country_Id, name, iso2Code)
VALUES ('3a756548-f66b-45d4-935c-3ee5bdb6bf8e', 'Aruba', 'AW'),
       ('b029f0b9-c983-48dd-a19b-043065c39581', 'Afghanistan', 'AF'),
       ('de27ae30-4c40-4421-bc89-c839bbef360f', 'Angola', 'AO'),
       ('f2c2c95b-f12f-4ccb-b98f-2c1f38ab75d2', 'Anguilla', 'AI'),
       ('44627dd7-190e-4bfd-a809-383f3e15debd', 'Åland Islands', 'AX'),
       ('070b686b-db84-4c02-a7a9-25f8bfb307d4', 'Albania', 'AL'),
       ('d39146d9-ef06-4cfc-9018-e977dee21aef', 'Andorra', 'AD'),
       ('576ec047-01c6-4e52-bd89-1eed651db0a4', 'United Arab Emirates', 'AE'),
       ('d96e87f1-9b85-402c-b4da-24c05a767a0b', 'Argentina', 'AR'),
       ('17d03e73-e335-4350-826a-2dbe20e63de5', 'Armenia', 'AM'),
       ('6bf9f377-9e48-41a8-a76a-d17d256b3fe8', 'American Samoa', 'AS'),
       ('3fc2206f-fd2c-4d12-931b-516d0c0898b9', 'French Southern Territories', 'TF'),
       ('690c62ab-d3bf-4cf8-9b75-9510d7d97807', 'Antigua and Barbuda', 'AG'),
       ('cbbd29e6-0ba1-4152-947d-e25d012f3803', 'Australia', 'AU'),
       ('c07cd7cb-ce44-4709-a9b6-9d8d2d568263', 'Austria', 'AT'),
       ('75cb1484-de52-4004-a23e-1d21248f9091', 'Azerbaijan', 'AZ'),
       ('523cde5a-2608-430d-aabc-3bbcd03b3988', 'Burundi', 'BI'),
       ('596a2af6-bab1-473e-bb9f-ad02d60b9fbb', 'Belgium', 'BE'),
       ('2d106957-b076-46ed-9696-9d39f4dbd9ee', 'Benin', 'BJ'),
       ('55c3a548-da37-4625-a17a-09ca6958f9cc', 'Bonaire, Sint Eustatius and Saba', 'BQ'),
       ('290a43b7-20ce-487a-85e4-538735ec8035', 'Burkina Faso', 'BF'),
       ('99104d63-8e61-4bc1-af2c-c81177f84399', 'Bangladesh', 'BD'),
       ('4bad384f-b67d-4463-b96d-0914231d9786', 'Bulgaria', 'BG'),
       ('cf238456-b50b-4d95-8dd4-6b1c7a73979e', 'Bahrain', 'BH'),
       ('d3ffd79e-e7b3-4f5c-b133-effc1da0ff54', 'Bahamas', 'BS'),
       ('b9f29ee1-882e-4f7b-bb34-e0f0939dea33', 'Bosnia and Herzegovina', 'BA'),
       ('98ea57e3-1da8-48f0-a441-881eaf65e28a', 'Saint Barthélemy', 'BL'),
       ('34218828-550b-4830-a367-656b20f9aad9', 'Belarus', 'BY'),
       ('9b96098b-8187-47f0-96bb-196f974f1121', 'Belize', 'BZ'),
       ('39febf31-62b1-45c2-8da7-894a8f09c8d5', 'Bermuda', 'BM'),
       ('a5916efc-cb5c-4c6d-b2ca-f7e912616a0f', 'Bolivia, Plurinational State of', 'BO'),
       ('62770f74-9794-4e79-aa22-2ce348b8b01c', 'Brazil', 'BR'),
       ('01c358a8-d841-485d-b1fc-924ea5f004c4', 'Barbados', 'BB'),
       ('b95b1271-5362-43ac-a90f-f251d84b937c', 'Brunei Darussalam', 'BN'),
       ('0feaebef-1d5e-430d-82fe-891f6d88bdeb', 'Bhutan', 'BT'),
       ('2ed4b80c-ce21-4aaa-adf0-3e8cf22e42c1', 'Bouvet Island', 'BV'),
       ('a8c733e2-4c36-4828-a659-78050de3c16d', 'Botswana', 'BW'),
       ('bfe59749-5467-43af-afb4-ca3b3c3c246c', 'Central African Republic', 'CF'),
       ('f45c4521-466f-48c8-930c-45c5a1391fe7', 'Canada', 'CA'),
       ('af39eb47-168b-42bf-9900-47ce4ab4775d', 'Cocos (Keeling) Islands', 'CC'),
       ('3c3010d1-a10f-47fe-b7cf-9635a3200669', 'Switzerland', 'CH'),
       ('7a36ac33-365f-402b-afeb-b1a776ec0bd0', 'Chile', 'CL'),
       ('34e8ed4a-68aa-4f6b-841b-e7acee6aa7a9', 'China', 'CN'),
       ('acf0703f-f7b5-4b46-9a70-5add2de4c7b1', 'Côte d''Ivoire', 'CI'),
       ('5afde5b5-01f3-4c23-9ce8-9118a36f16fa', 'Cameroon', 'CM'),
       ('d59dc4c7-6e3a-46dd-be04-7c7aa44c46d0', 'Congo, The Democratic Republic of the', 'CD'),
       ('d3d9c73d-4d9e-4fb0-80d7-5e5776c05a16', 'Congo', 'CG'),
       ('6efde65a-0a4f-4575-8911-776740d4d2ea', 'Cook Islands', 'CK'),
       ('08a40323-8363-4f69-b0d2-6abfb5a5f26a', 'Colombia', 'CO'),
       ('c99b4197-66e3-4bcf-9e89-b496f99299d9', 'Comoros', 'KM'),
       ('c2d26ae0-48ae-4de6-8744-98c15a11b24d', 'Cabo Verde', 'CV'),
       ('116012fa-8e9b-4898-aa7a-9b3370716d8d', 'Costa Rica', 'CR'),
       ('d7b24d5f-f614-499e-b91f-f3a63ad1002b', 'Cuba', 'CU'),
       ('74697354-2a3e-4bed-83f1-f871e31b0d66', 'Curaçao', 'CW'),
       ('59c84959-572d-465c-978b-eeee499e4368', 'Christmas Island', 'CX'),
       ('dbeab57d-1ef6-4803-91b5-c0e2f92960bd', 'Cayman Islands', 'KY'),
       ('8f0f627e-6389-4683-b9ab-139832040a0a', 'Cyprus', 'CY'),
       ('f0e5db6d-8aed-4f06-97d5-9ca5e021a59a', 'Czechia', 'CZ'),
       ('ddd73616-2461-42b9-acc6-351113e6d735', 'Germany', 'DE'),
       ('38a411bd-317e-4244-ad11-7cd356dc4fd3', 'Djibouti', 'DJ'),
       ('b9f41046-f789-449c-bfec-16b5f96c977d', 'Dominica', 'DM'),
       ('9d4c4a82-a007-443a-aa36-88c5cf607280', 'Denmark', 'DK'),
       ('315e9604-eac3-4620-b844-b26f44324bab', 'Dominican Republic', 'DO'),
       ('a60e66aa-27bd-402b-bd1d-4637471b75d9', 'Algeria', 'DZ'),
       ('0fbea3d9-7f5b-45da-9648-f96c10de9420', 'Ecuador', 'EC'),
       ('56585b13-71b8-4180-be15-6b588a7238e2', 'Egypt', 'EG'),
       ('6c815a6a-967c-4a48-bb47-33b3dd40fc10', 'Eritrea', 'ER'),
       ('6bb6c227-cf4b-473b-854b-cc0bfb7f77a1', 'Western Sahara', 'EH'),
       ('bd02204f-5700-4696-8790-65c97d0c9268', 'Spain', 'ES'),
       ('cff48969-c5a1-48be-989b-65c152601aaf', 'Estonia', 'EE'),
       ('b20e3a09-b367-4508-9972-3b648025f084', 'Ethiopia', 'ET'),
       ('59ee4e99-b9e9-4635-8643-af5e3ad3268a', 'Finland', 'FI'),
       ('4b57a22a-10a5-43ef-a9d9-50d61fb82b65', 'Fiji', 'FJ'),
       ('b2b788f7-a2af-474c-b088-a25affc8aa7b', 'Falkland Islands (Malvinas)', 'FK'),
       ('5ef168d6-2cf6-4956-9ba4-ad0feb52c204', 'France', 'FR'),
       ('e8906eee-8fd1-43fc-a951-66c2f2168232', 'Faroe Islands', 'FO'),
       ('3d4122a0-323b-40b4-8975-2ed15a699ad0', 'Micronesia, Federated States of', 'FM'),
       ('fdd82c29-b647-4f19-92cf-c9f512c2da69', 'Gabon', 'GA'),
       ('1eeb34ab-1b97-43db-ae1d-47b40e51b066', 'United Kingdom', 'GB'),
       ('fb7021e1-5380-45d1-946c-362bfc447dee', 'Georgia', 'GE'),
       ('4d589ebc-3f84-4448-b093-c311e19cffb2', 'Guernsey', 'GG'),
       ('3db85348-d3c9-4252-a6d5-a2844e2c3f4f', 'Ghana', 'GH'),
       ('8e79e771-b9c5-4ff7-9d83-d261a9c1634d', 'Gibraltar', 'GI'),
       ('d6e7eeeb-6d33-48af-84c5-d0f0b6af9627', 'Guinea', 'GN'),
       ('330e74af-9e5d-4fec-b15c-258cfcbab590', 'Guadeloupe', 'GP'),
       ('3e1a6714-5043-4b42-be1c-58ae2116add9', 'Gambia', 'GM'),
       ('71086478-cde6-41fe-8019-dd4d28385384', 'Guinea-Bissau', 'GW'),
       ('e03b11f2-81cc-49bc-8145-116b0f5b988f', 'Equatorial Guinea', 'GQ'),
       ('c61e6966-1540-4386-9584-9e86ca9718d6', 'Greece', 'GR'),
       ('7785fa6c-0b3e-4668-aceb-89557fd66980', 'Grenada', 'GD'),
       ('e171defc-2e08-4a62-bbec-75a48be8c82f', 'Greenland', 'GL'),
       ('070b5ee1-d5c9-4969-ad31-711b84a3b4c1', 'Guatemala', 'GT'),
       ('d314b925-0e0a-42b4-840b-fd4bcd65739f', 'French Guiana', 'GF'),
       ('d8c48f50-ac54-4228-a4cc-23d846085139', 'Guam', 'GU'),
       ('cef2254c-e865-4afc-9e2d-708a7b334ad5', 'Guyana', 'GY'),
       ('cac43c3d-094f-449d-abbb-abb064facdd8', 'Hong Kong', 'HK'),
       ('c79bf075-e792-4d5d-b9bd-73fdda0d0bfe', 'Heard Island and McDonald Islands', 'HM'),
       ('e160ef75-00ea-4d5a-ab5a-92d084e3b443', 'Honduras', 'HN'),
       ('54bffe4d-9aae-43d2-ade1-05041f0b1943', 'Croatia', 'HR'),
       ('5cd15ffd-47a0-47b2-8ec4-692ab392ecd0', 'Haiti', 'HT'),
       ('2700b3ca-8419-45dc-8407-27bdb4d6493a', 'Hungary', 'HU'),
       ('6a53dee2-fe67-4117-865a-8014670845ae', 'Indonesia', 'ID'),
       ('b70a7983-0bb7-4305-8308-40737ee02cf7', 'Isle of Man', 'IM'),
       ('3973b9b5-32a0-4265-ac52-ae6baf3ece85', 'India', 'IN'),
       ('4ba6f5db-bedb-42d0-8f5a-adc73354170c', 'British Indian Ocean Territory', 'IO'),
       ('62531ac2-b8f9-4f4b-b9e4-0b11e1d353b4', 'Ireland', 'IE'),
       ('4209729b-34f4-499b-902f-1a56196200d8', 'Iran, Islamic Republic of', 'IR'),
       ('f703150b-01db-4d50-86fc-3bc30788c9f9', 'Iraq', 'IQ'),
       ('0564b5b5-3045-4b59-979c-d7a6faff1bab', 'Iceland', 'IS'),
       ('737e30de-b9a1-4d0c-a085-080d8d828c56', 'Israel', 'IL'),
       ('b2b58756-c57c-4410-9aa4-301667a0f00a', 'Italy', 'IT'),
       ('b4036c43-4b74-4ec0-ae3e-705f3a340a8f', 'Jamaica', 'JM'),
       ('7ab1b60e-0cec-4e37-be46-45d176823d7a', 'Jersey', 'JE'),
       ('f9f9f53d-18f9-432c-b8f7-de810c73b295', 'Jordan', 'JO'),
       ('b70b71a2-cd2c-4d55-a9b0-a82c5b715c9e', 'Japan', 'JP'),
       ('38d047f0-21ed-4a3f-b705-8de65bd95078', 'Kazakhstan', 'KZ'),
       ('3a84a2dc-93b5-4192-910a-5cbc784828b6', 'Kenya', 'KE'),
       ('6d342fc2-309c-43f7-b922-36bf1de408c3', 'Kyrgyzstan', 'KG'),
       ('536406e8-84a4-4290-b098-7e31147717d7', 'Cambodia', 'KH'),
       ('2eb3ac73-e426-4b05-821b-22ab3a59853a', 'Kiribati', 'KI'),
       ('b6d2f9d5-5285-479c-b298-3bd15bbbe0d0', 'Saint Kitts and Nevis', 'KN'),
       ('ff1db781-c1b6-4f2f-9117-4380c367f015', 'Korea, Republic of', 'KR'),
       ('c372124d-36b0-4c2b-b591-d7aef3f0aa29', 'Kuwait', 'KW'),
       ('879a72e6-6bc7-4359-9073-19a7c0cd8af0', 'Lao People''s Democratic Republic', 'LA'),
       ('7ac076a9-df17-4a4d-96ea-52e6fb27323f', 'Lebanon', 'LB'),
       ('11cb72df-7231-432d-b815-6b4173e043b1', 'Liberia', 'LR'),
       ('83fd6349-b56b-43ff-a47a-d72961812cd3', 'Libya', 'LY'),
       ('d04a0fc6-c1db-46cd-8e33-bac87a81c0af', 'Saint Lucia', 'LC'),
       ('d913b7df-a2cb-4ddd-8428-7314870547bd', 'Liechtenstein', 'LI'),
       ('6807d7b5-3be6-4bf8-b896-c974514af32f', 'Sri Lanka', 'LK'),
       ('31cba853-09b2-4d0d-999b-0dea9ae8c1ad', 'Lesotho', 'LS'),
       ('19f7c6f8-cbce-424f-9bc6-05dab66ea826', 'Lithuania', 'LT'),
       ('e437dea6-ee0e-4297-8977-9606feabd159', 'Luxembourg', 'LU'),
       ('d05db4c9-a5bc-4bc4-b5e3-a592d9f0ed40', 'Latvia', 'LV'),
       ('88fef247-f84a-4fd9-ae15-48605de551ff', 'Macao', 'MO'),
       ('54d92d16-bb93-4bf2-9c88-d62e6bdc5034', 'Saint Martin (French part)', 'MF'),
       ('98f67eef-d15c-484e-b002-c92b7cbd1a8b', 'Morocco', 'MA'),
       ('08a25a78-d005-421a-b65f-928c14794818', 'Monaco', 'MC'),
       ('05aca8d7-5648-4b5e-9dd7-a2502e9998e1', 'Moldova, Republic of', 'MD'),
       ('94f46829-c3cc-4de2-90a1-75980fffe3fb', 'Madagascar', 'MG'),
       ('894c032f-8676-4ad2-97eb-09f0640aff2c', 'Maldives', 'MV'),
       ('6cb8116c-e2ce-4306-95f8-dc00b7471d09', 'Mexico', 'MX'),
       ('ffb63cf7-024f-44d6-af19-ddc3009de23f', 'Marshall Islands', 'MH'),
       ('e88844d0-5f30-42a2-a218-78c209090178', 'North Macedonia', 'MK'),
       ('ecccede7-ec3b-4283-b881-0e5f95c86ac3', 'Mali', 'ML'),
       ('8d6e5fbf-4872-4a9d-9cc1-83f78ab017a3', 'Malta', 'MT'),
       ('d5e16e91-0210-4bd0-b695-0a2029aaa65f', 'Myanmar', 'MM'),
       ('affdddae-9eb5-4b9a-b9ea-a863f1de4e89', 'Montenegro', 'ME'),
       ('312a613f-4079-413b-962b-1c7f40ea0f9a', 'Mongolia', 'MN'),
       ('3a96c8b4-053a-4575-8e4f-ed3a84e918e7', 'Northern Mariana Islands', 'MP'),
       ('de1adb71-228b-437c-a64e-e51de0869fc1', 'Mozambique', 'MZ'),
       ('52fcf399-f34f-40ac-9f77-8fd0ea725247', 'Mauritania', 'MR'),
       ('1c45bf48-da83-42e8-8e9f-ff718489d596', 'Montserrat', 'MS'),
       ('31050c5d-08c9-4de3-8478-1391a9ab56ba', 'Martinique', 'MQ'),
       ('44a6e2fc-266f-44ad-96d0-53883af8533e', 'Mauritius', 'MU'),
       ('0e7414ff-5e85-4ade-8c27-fad6e77fe831', 'Malawi', 'MW'),
       ('91b70685-be91-4bec-9fab-b4c8b23ec4df', 'Malaysia', 'MY'),
       ('bbdef952-0110-46dd-b41a-2f410c7f41c5', 'Mayotte', 'YT'),
       ('41f8b0aa-d1ee-4267-aa77-1bd8d42a49d1', 'Namibia', 'NA'),
       ('6a29098c-3a7d-4e44-902f-1af542ddbaf3', 'New Caledonia', 'NC'),
       ('16757015-854e-4ae3-8d17-50c74a3647f3', 'Niger', 'NE'),
       ('b0b9e03e-1b42-4768-8904-82168a19d63e', 'Norfolk Island', 'NF'),
       ('15471826-f120-4f6b-a78b-6e30ef6f8996', 'Nigeria', 'NG'),
       ('60901fac-4aa4-4b0e-b22a-0b72a4bb2c58', 'Nicaragua', 'NI'),
       ('5d1770ad-239d-4b59-81f5-772ba7b1aab8', 'Niue', 'NU'),
       ('07b83cb3-01dd-4f58-9717-bff8aadc8120', 'Netherlands', 'NL'),
       ('31715f38-aeb1-4cc4-bc18-2cd0e6dfd9a4', 'Norway', 'NO'),
       ('8f110f59-481b-4edd-b36c-184514f2ae9b', 'Nepal', 'NP'),
       ('b55dbf8d-aedc-4521-8d84-368480b8fac1', 'Nauru', 'NR'),
       ('d4185840-5630-4e6e-ae8e-6025c5afa174', 'New Zealand', 'NZ'),
       ('11b2aa9d-5ece-4156-8812-86699dda2c90', 'Oman', 'OM'),
       ('f334456b-2c53-4dc1-abb3-8bea1cd52ece', 'Pakistan', 'PK'),
       ('fddedbf7-9eef-4fcc-9596-2fb01f07d3a0', 'Panama', 'PA'),
       ('3abba98b-5799-47cc-a0d5-c909ccb39ca8', 'Pitcairn', 'PN'),
       ('cf34d497-a13c-4162-a9bd-de48fbf99811', 'Peru', 'PE'),
       ('50438c4e-0928-4b2f-bffe-cdf8fd21adfa', 'Philippines', 'PH'),
       ('1bd43e52-29bd-49d1-a6d3-cbdb8e04dc35', 'Palau', 'PW'),
       ('0371e14a-9a0b-428b-99b0-019d67c38030', 'Papua New Guinea', 'PG'),
       ('b0ef094f-a60f-4f25-9af3-c8b216ac8ce2', 'Poland', 'PL'),
       ('8e33e9ce-b871-4b43-80ee-15e31468087c', 'Puerto Rico', 'PR'),
       ('9f9b5946-047e-485b-8475-9503644cd3a0', 'Korea, Democratic People''s Republic of', 'KP'),
       ('1f431fdf-966d-4400-9342-8675eb1e8065', 'Portugal', 'PT'),
       ('2792cd4e-23b1-4ef9-ba3d-16f1ceedb34a', 'Paraguay', 'PY'),
       ('17813df5-3ad2-4a3f-8959-79361d0aa0da', 'Palestine, State of', 'PS'),
       ('b0d4185e-95e6-4f56-a96d-b7402fd918b1', 'French Polynesia', 'PF'),
       ('d24422cb-3d5f-4c99-8043-2c335e524a29', 'Qatar', 'QA'),
       ('9aec6ce4-7956-4326-b7b1-1fd5a26d962f', 'Réunion', 'RE'),
       ('3b832b24-2113-4247-9f26-047335adbc2b', 'Romania', 'RO'),
       ('a23a79ea-5c6e-46d0-9cde-a8d9e0ff4eef', 'Russian Federation', 'RU'),
       ('bb2fca1a-f6aa-4c75-92f6-5bb79e08cd2a', 'Rwanda', 'RW'),
       ('c68ca97a-ae41-4bbb-b03d-863e4b95e6c7', 'Saudi Arabia', 'SA'),
       ('cf47b4da-f3b8-4b51-b953-ed054fa39e17', 'Sudan', 'SD'),
       ('19c244d2-7a25-4af3-878b-d26f84727f4f', 'Senegal', 'SN'),
       ('33c00a24-0eab-4132-97ca-77f7b4a8e3bf', 'Singapore', 'SG'),
       ('a606216d-151f-48c3-b193-c5e6709a5fb4', 'South Georgia and the South Sandwich Islands', 'GS'),
       ('52d83429-dd62-4c90-a5eb-c1caf311ee21', 'Saint Helena, Ascension and Tristan da Cunha', 'SH'),
       ('3bced042-0bcc-4101-983c-ea2aefe0201b', 'Svalbard and Jan Mayen', 'SJ'),
       ('c6d35adc-adb2-4b04-9665-461ecca81c7d', 'Solomon Islands', 'SB'),
       ('93cd2070-508f-4cb8-b21c-bc39ae08720d', 'Sierra Leone', 'SL'),
       ('3653311e-c476-47bc-b547-546ceeba724a', 'El Salvador', 'SV'),
       ('e7a22136-7f86-4da5-9e30-543b6e1a0832', 'San Marino', 'SM'),
       ('53c6e0e8-581b-41e4-9036-3df4a579a4e8', 'Somalia', 'SO'),
       ('e5c6333c-864b-41ed-8701-1933ae8193ad', 'Saint Pierre and Miquelon', 'PM'),
       ('1184d842-7563-46f3-8e3f-5be51ce7b7f3', 'Serbia', 'RS'),
       ('f27a8aa1-7708-4fd0-b9c2-a281178c822b', 'South Sudan', 'SS'),
       ('4e73e440-9ba4-4407-b78b-99799483fec9', 'Sao Tome and Principe', 'ST'),
       ('a62f0e65-bbe4-4163-b9e9-9073a1d08649', 'Suriname', 'SR'),
       ('7789a40e-52dc-4d4f-a9cb-12b7c31b6722', 'Slovakia', 'SK'),
       ('9a2e2d89-94f4-4934-8bba-e64144d518ad', 'Slovenia', 'SI'),
       ('35055a1d-29c5-40af-b5f9-20f30e3b840e', 'Sweden', 'SE'),
       ('b78d667c-632b-4d9d-a79e-56890d96977f', 'Eswatini', 'SZ'),
       ('54b853fe-8115-4550-9279-5d6c3cd08cd3', 'Sint Maarten (Dutch part)', 'SX'),
       ('2743fb6d-8074-48d5-956c-93aa87a2e52f', 'Seychelles', 'SC'),
       ('b9e1db6b-8817-4bf1-8c63-bc0c06ee0095', 'Syrian Arab Republic', 'SY'),
       ('904613a9-4ac9-498b-8f42-f3655f0473e4', 'Turks and Caicos Islands', 'TC'),
       ('1e40d7c6-c496-4a63-a6c0-55cd843d5d25', 'Chad', 'TD'),
       ('127333f8-aad4-46ae-bff7-325b254fbfcb', 'Togo', 'TG'),
       ('e935540e-56b8-40f0-b246-d268024d43f5', 'Thailand', 'TH'),
       ('7699e216-fe2a-4f9b-8b42-ea2b10a358f9', 'Tajikistan', 'TJ'),
       ('6d4985da-10ea-4af1-8563-e890f181c8f8', 'Tokelau', 'TK'),
       ('3bae0e64-3473-4534-8b0e-e8970f0cbaf5', 'Turkmenistan', 'TM'),
       ('c0561008-a425-4a22-be2f-1b5bdff29b08', 'Timor-Leste', 'TL'),
       ('703cfcf3-a779-4500-b8b5-c510e93eabf0', 'Tonga', 'TO'),
       ('63cb0034-c27d-4539-9426-e47817186f30', 'Trinidad and Tobago', 'TT'),
       ('c7e9c518-7282-4f46-a469-05aecfa9f846', 'Tunisia', 'TN'),
       ('107a094f-bb09-47f9-917d-93babf622b7d', 'Türkiye', 'TR'),
       ('4c720aee-281f-496f-a056-068c64b5999b', 'Tuvalu', 'TV'),
       ('a8ed4b46-ebf7-4ed6-b3ef-dedfbeb36cbd', 'Taiwan, Province of China', 'TW'),
       ('96ac58eb-af2e-4354-8d6f-2ed75364b6ce', 'Tanzania, United Republic of', 'TZ'),
       ('c4f7c107-0b44-4823-919f-7a2032096221', 'Uganda', 'UG'),
       ('7b823290-d22a-4acc-b3d7-2ba86d6a59b2', 'Ukraine', 'UA'),
       ('8dc994b0-e218-4fdd-94e2-e8848861aac7', 'United States Minor Outlying Islands', 'UM'),
       ('472bf7e2-25cb-4d91-823b-9883b93bb423', 'Uruguay', 'UY'),
       ('628fce3f-af06-43e2-87d9-645f0ee9e056', 'United States', 'US'),
       ('362721ad-72c9-4582-9d96-5ca531e05b05', 'Uzbekistan', 'UZ'),
       ('46ea8070-dea8-46fb-89bd-4b52547e39db', 'Holy See (Vatican City State)', 'VA'),
       ('558b61ed-d516-4f46-9feb-3a518cfb0bc1', 'Saint Vincent and the Grenadines', 'VC'),
       ('85438514-1702-403a-bc3b-2347b836678b', 'Venezuela, Bolivarian Republic of', 'VE'),
       ('f12df3da-27b3-42c6-a282-6cbd10b6e28e', 'Virgin Islands, British', 'VG'),
       ('ddc201d4-a6f6-469a-969a-b2d6ba285e61', 'Virgin Islands, U.S.', 'VI'),
       ('7cbc1abc-8d03-4f06-80fa-7dfe02de26bd', 'Viet Nam', 'VN'),
       ('83c63445-d651-404a-9e17-3df860049d9e', 'Vanuatu', 'VU'),
       ('6c95aa8e-376c-4542-9df9-2a2d75a444c9', 'Wallis and Futuna', 'WF'),
       ('4bf06075-17e7-472d-8d67-bd33d2027d65', 'Samoa', 'WS'),
       ('2f2f7211-ff03-43b5-b3f3-4f8407670045', 'Yemen', 'YE'),
       ('73e9f901-e6e7-4af4-b7f3-696310a4d71d', 'South Africa', 'ZA'),
       ('71864c56-a025-43cd-9249-0ae11cdca8f1', 'Zambia', 'ZM'),
       ('9e02a546-ec64-4892-84aa-991af13d7743', 'Zimbabwe', 'ZW');

INSERT INTO spring.user(role, country_id, email, first_name, last_name, password, user_id, user_name, salutation,
                        gender, version)
VALUES ('ADMIN', 'c07cd7cb-ce44-4709-a9b6-9d8d2d568263', 'ww23e016@technikum-wien.at', 'Osama', 'Madani',
        '$2a$10$9jRHv9Ka2VoLITSGW5bKEOnPH4OYGDuu6R7V1Ufc.LJSAa/XnQdUu',
        'c07cd7cb-ce44-4709-a9b6-9d8d2d568263', 'osamathebest', 'Mr.', 'MALE', 0);

-- create another 2 normal users
INSERT INTO spring.user(role, country_id, email, first_name, last_name, password, user_id, user_name, salutation,
                        gender, version)
VALUES ('USER', 'c07cd7cb-ce44-4709-a9b6-9d8d2d568263', 'os235.ma22@gmail.com', 'Osama', 'Madani',
        '$2a$10$9jRHv9Ka2VoLITSGW5bKEOnPH4OYGDuu6R7V1Ufc.LJSAa/XnQdUu',
        'c07cd7cb-ce44-4709-a9b6-9d8d2d568264', 'oneandtheonly', 'Master of Disasters', 'MALE', 0);

INSERT INTO spring.user(role, country_id, email, first_name, last_name, password, user_id, user_name, salutation,
                        gender, version)
VALUES ('USER', 'c07cd7cb-ce44-4709-a9b6-9d8d2d568263', 'forkg71@gmail.com', 'Osama', 'Madani',
        '$2a$10$9jRHv9Ka2VoLITSGW5bKEOnPH4OYGDuu6R7V1Ufc.LJSAa/XnQdUu',
        'c07cd7cb-ce44-4709-a9b6-9d8d2d568265', 'osamaallmighty', 'Master of Disasters', 'MALE', 0);

-- create a park
INSERT INTO spring.park(park_id, name, description, street_number, city, zip_code, country_id, version)
VALUES ('c07cd7cb-ce44-4709-a9b6-9d8d2d568263', 'Prater',
        'The Prater is a large public park in Vienna''s 2nd district (Leopoldstadt). The Wurstelprater amusement park, often simply called "Prater", stands in one corner of the Wiener Prater and includes the Wiener Riesenrad Ferris wheel.',
        '1020', 'Vienna', '1020', 'c07cd7cb-ce44-4709-a9b6-9d8d2d568263', 0);

-- create a park
INSERT INTO spring.park(park_id, name, description, street_number, city, zip_code, country_id, version)
VALUES ('c07cd7cb-ce44-4709-32af-9d8d2d568264', 'Schönbrunn',
        'Schönbrunn Palace is a former imperial summer residence located in Vienna, Austria. The 1,441-room Baroque palace is one of the most important architectural, cultural, and historical monuments in the country.',
        '1130', 'Vienna', '1130', 'c07cd7cb-ce44-4709-a9b6-9d8d2d568263', 0);

-- user oneandtheonly create an event
INSERT INTO spring.event(event.version, event_2_user_id, event_id, title, description, startts, endts, event_park_id)
VALUES (0, 'c07cd7cb-ce44-4709-a9b6-9d8d2d568264', 'c07cd7cb-ce44-4709-a9b6-9d8d2d568263', 'Prater Event',
        'The Prater is a large public park in Vienna''s 2nd district (Leopoldstadt). The Wurstelprater amusement park, often simply called "Prater", stands in one corner of the Wiener Prater and includes the Wiener Riesenrad Ferris wheel.',
        NOW() + INTERVAL 1 DAY, NOW() + INTERVAL 1 DAY + INTERVAL 30 MINUTE, 'c07cd7cb-ce44-4709-a9b6-9d8d2d568263');

-- user OsamaAllMighty create an event
INSERT INTO spring.event(event.version, event_2_user_id, event_id, title, description, startts, endts, event_park_id)
VALUES (0, 'c07cd7cb-ce44-4709-a9b6-9d8d2d568265', 'c07cd7cb-ca44-4709-a9b6-9d8d2d568266', 'Schönbrunn Event',
        'Schönbrunn Palace is a former imperial summer residence located in Vienna, Austria. The 1,441-room Baroque palace is one of the most important architectural, cultural, and historical monuments in the country.',
        NOW() + INTERVAL 2 DAY, NOW() + INTERVAL 2 DAY + INTERVAL 30 MINUTE, 'c07cd7cb-ce44-4709-32af-9d8d2d568264');

-- Additional events for Prater park
INSERT INTO spring.event(event.version, event_2_user_id, event_id, title, description, startts, endts, event_park_id)
VALUES
    (0, 'c07cd7cb-ce44-4709-a9b6-9d8d2d568264', 'aaaaaaaa-aaaa-aaaa-aaaa-aaaaaaaaaaaa', 'Prater Summer Festival', 'Join us for a fun-filled summer festival at Prater with games, food, and music.', NOW() + INTERVAL 3 DAY, NOW() + INTERVAL 3 DAY + INTERVAL 30 MINUTE, 'c07cd7cb-ce44-4709-a9b6-9d8d2d568263'),
    (0, 'c07cd7cb-ce44-4709-a9b6-9d8d2d568264', 'bbbbbbbb-bbbb-bbbb-bbbb-bbbbbbbbbbbb', 'Prater Music Concert', 'Experience an amazing live music concert at Prater featuring local bands.', NOW() + INTERVAL 4 DAY, NOW() + INTERVAL 4 DAY + INTERVAL 30 MINUTE, 'c07cd7cb-ce44-4709-a9b6-9d8d2d568263'),
    (0, 'c07cd7cb-ce44-4709-a9b6-9d8d2d568264', 'cccccccc-cccc-cccc-cccc-cccccccccccc', 'Prater Food Fair', 'Taste a variety of delicious foods from different cuisines at the Prater Food Fair.', NOW() + INTERVAL 5 DAY, NOW() + INTERVAL 5 DAY + INTERVAL 30 MINUTE, 'c07cd7cb-ce44-4709-a9b6-9d8d2d568263'),
    (0, 'c07cd7cb-ce44-4709-a9b6-9d8d2d568264', 'dddddddd-dddd-dddd-dddd-dddddddddddd', 'Prater Art Exhibition', 'Explore beautiful artworks by local artists at the Prater Art Exhibition.', NOW() + INTERVAL 6 DAY, NOW() + INTERVAL 6 DAY + INTERVAL 30 MINUTE, 'c07cd7cb-ce44-4709-a9b6-9d8d2d568263'),
    (0, 'c07cd7cb-ce44-4709-a9b6-9d8d2d568264', 'eeeeeeee-eeee-eeee-eeee-eeeeeeeeeeee', 'Prater Movie Night', 'Enjoy a movie night under the stars at Prater.', NOW() + INTERVAL 7 DAY, NOW() + INTERVAL 7 DAY + INTERVAL 30 MINUTE, 'c07cd7cb-ce44-4709-a9b6-9d8d2d568263'),
    (0, 'c07cd7cb-ce44-4709-a9b6-9d8d2d568264', 'ffffffff-ffff-ffff-ffff-ffffffffffff', 'Prater Sports Day', 'Participate in various sports activities at Prater.', NOW() + INTERVAL 8 DAY, NOW() + INTERVAL 8 DAY + INTERVAL 30 MINUTE, 'c07cd7cb-ce44-4709-a9b6-9d8d2d568263'),
    (0, 'c07cd7cb-ce44-4709-a9b6-9d8d2d568264', '00000000-0000-0000-0000-000000000000', 'Prater Book Fair', 'Explore a wide range of books at the Prater Book Fair.', NOW() + INTERVAL 9 DAY, NOW() + INTERVAL 9 DAY + INTERVAL 30 MINUTE, 'c07cd7cb-ce44-4709-a9b6-9d8d2d568263'),
    (0, 'c07cd7cb-ce44-4709-a9b6-9d8d2d568264', '11111111-1111-1111-1111-111111111111', 'Prater Science Expo', 'Discover scientific wonders at the Prater Science Expo.', NOW() + INTERVAL 10 DAY, NOW() + INTERVAL 10 DAY + INTERVAL 30 MINUTE, 'c07cd7cb-ce44-4709-a9b6-9d8d2d568263'),
    (0, 'c07cd7cb-ce44-4709-a9b6-9d8d2d568264', '22222222-2222-2222-2222-222222222222', 'Prater Dance Festival', 'Join us for a vibrant dance festival at Prater.', NOW() + INTERVAL 11 DAY, NOW() + INTERVAL 11 DAY + INTERVAL 30 MINUTE, 'c07cd7cb-ce44-4709-a9b6-9d8d2d568263');

-- Additional events for Schönbrunn park
INSERT INTO spring.event(event.version, event_2_user_id, event_id, title, description, startts, endts, event_park_id)
VALUES
    (0, 'c07cd7cb-ce44-4709-a9b6-9d8d2d568265', '33333333-3333-3333-3333-333333333333', 'Schönbrunn Garden Tour', 'Take a guided tour of the beautiful gardens at Schönbrunn Palace.', NOW() + INTERVAL 12 DAY, NOW() + INTERVAL 12 DAY + INTERVAL 30 MINUTE, 'c07cd7cb-ce44-4709-32af-9d8d2d568264'),
    (0, 'c07cd7cb-ce44-4709-a9b6-9d8d2d568265', '44444444-4444-4444-4444-444444444444', 'Schönbrunn Historical Walk', 'Learn about the rich history of Schönbrunn Palace on this historical walk.', NOW() + INTERVAL 13 DAY, NOW() + INTERVAL 13 DAY + INTERVAL 30 MINUTE, 'c07cd7cb-ce44-4709-32af-9d8d2d568264'),
    (0, 'c07cd7cb-ce44-4709-a9b6-9d8d2d568265', '55555555-5555-5555-5555-555555555555', 'Schönbrunn Family Picnic', 'Enjoy a relaxing family picnic in the scenic grounds of Schönbrunn Palace.', NOW() + INTERVAL 14 DAY, NOW() + INTERVAL 14 DAY + INTERVAL 30 MINUTE, 'c07cd7cb-ce44-4709-32af-9d8d2d568264'),
    (0, 'c07cd7cb-ce44-4709-a9b6-9d8d2d568265', '66666666-6666-6666-6666-666666666666', 'Schönbrunn Evening Gala', 'Attend an elegant evening gala at Schönbrunn Palace with live music and fine dining.', NOW() + INTERVAL 15 DAY, NOW() + INTERVAL 15 DAY + INTERVAL 30 MINUTE, 'c07cd7cb-ce44-4709-32af-9d8d2d568264'),
    (0, 'c07cd7cb-ce44-4709-a9b6-9d8d2d568265', '77777777-7777-7777-7777-777777777777', 'Schönbrunn Photography Workshop', 'Learn photography skills at Schönbrunn Palace.', NOW() + INTERVAL 16 DAY, NOW() + INTERVAL 16 DAY + INTERVAL 30 MINUTE, 'c07cd7cb-ce44-4709-32af-9d8d2d568264'),
    (0, 'c07cd7cb-ce44-4709-a9b6-9d8d2d568265', '88888888-8888-8888-8888-888888888888', 'Schönbrunn Yoga Session', 'Relax with a yoga session in the gardens of Schönbrunn.', NOW() + INTERVAL 17 DAY, NOW() + INTERVAL 17 DAY + INTERVAL 30 MINUTE, 'c07cd7cb-ce44-4709-32af-9d8d2d568264'),
    (0, 'c07cd7cb-ce44-4709-a9b6-9d8d2d568265', '99999999-9999-9999-9999-999999999999', 'Schönbrunn Wine Tasting', 'Enjoy a wine tasting event at Schönbrunn Palace.', NOW() + INTERVAL 18 DAY, NOW() + INTERVAL 18 DAY + INTERVAL 30 MINUTE, 'c07cd7cb-ce44-4709-32af-9d8d2d568264'),
    (0, 'c07cd7cb-ce44-4709-a9b6-9d8d2d568265', 'aaetetet-aaaa-aaaa-aaaa-aaaaaaaaaaaa', 'Schönbrunn Classical Concert', 'Attend a classical music concert at Schönbrunn Palace.', NOW() + INTERVAL 19 DAY, NOW() + INTERVAL 19 DAY + INTERVAL 30 MINUTE, 'c07cd7cb-ce44-4709-32af-9d8d2d568264'),
    (0, 'c07cd7cb-ce44-4709-a9b6-9d8d2d568265', '3535a53b-bbbb-bbbb-bbbb-bbbbbbbbbbbb', 'Schönbrunn Art Workshop', 'Participate in an art workshop at Schönbrunn Palace.', NOW() + INTERVAL 20 DAY, NOW() + INTERVAL 20 DAY + INTERVAL 30 MINUTE, 'c07cd7cb-ce44-4709-32af-9d8d2d568264');

INSERT INTO spring.event_tag(event_tag_id, name, version)
VALUES ('d5d58630-03b4-4089-8da9-fed915a11a7c', 'Festival', 0),
       ('6f581306-3d69-4481-a769-f692920ea14d', 'Music', 0),
       ('9de57aae-9658-405e-966d-01a56aaf9e13', 'Tour', 0),
       ('2a08a88d-1c1c-4e11-b52c-6d9c0f11f05b', 'Walk', 0);

INSERT INTO spring.event_event_tag(event_tag_id, event_id)
VALUES ('9de57aae-9658-405e-966d-01a56aaf9e13', '33333333-3333-3333-3333-333333333333'),
       ('2a08a88d-1c1c-4e11-b52c-6d9c0f11f05b', '33333333-3333-3333-3333-333333333333'),
       ('2a08a88d-1c1c-4e11-b52c-6d9c0f11f05b', '44444444-4444-4444-4444-444444444444'),
       ('9de57aae-9658-405e-966d-01a56aaf9e13', '44444444-4444-4444-4444-444444444444');

INSERT INTO spring.event_joined_user(event_id, user_id)
VALUES ('00000000-0000-0000-0000-000000000000', 'c07cd7cb-ce44-4709-a9b6-9d8d2d568263'),
       ('00000000-0000-0000-0000-000000000000', 'c07cd7cb-ce44-4709-a9b6-9d8d2d568264'),
       ('00000000-0000-0000-0000-000000000000', 'c07cd7cb-ce44-4709-a9b6-9d8d2d568265'),
       ('11111111-1111-1111-1111-111111111111', 'c07cd7cb-ce44-4709-a9b6-9d8d2d568264'),
       ('c07cd7cb-ce44-4709-a9b6-9d8d2d568263', 'c07cd7cb-ce44-4709-a9b6-9d8d2d568264'),
       ('22222222-2222-2222-2222-222222222222', 'c07cd7cb-ce44-4709-a9b6-9d8d2d568264'),
       ('33333333-3333-3333-3333-333333333333', 'c07cd7cb-ce44-4709-a9b6-9d8d2d568265'),
       ('44444444-4444-4444-4444-444444444444', 'c07cd7cb-ce44-4709-a9b6-9d8d2d568265'),
       ('55555555-5555-5555-5555-555555555555', 'c07cd7cb-ce44-4709-a9b6-9d8d2d568265'),
       ('66666666-6666-6666-6666-666666666666', 'c07cd7cb-ce44-4709-a9b6-9d8d2d568265'),
       ('77777777-7777-7777-7777-777777777777', 'c07cd7cb-ce44-4709-a9b6-9d8d2d568265'),
       ('88888888-8888-8888-8888-888888888888', 'c07cd7cb-ce44-4709-a9b6-9d8d2d568265'),
       ('99999999-9999-9999-9999-999999999999', 'c07cd7cb-ce44-4709-a9b6-9d8d2d568265'),
       ('aaetetet-aaaa-aaaa-aaaa-aaaaaaaaaaaa', 'c07cd7cb-ce44-4709-a9b6-9d8d2d568265'),
       ('3535a53b-bbbb-bbbb-bbbb-bbbbbbbbbbbb', 'c07cd7cb-ce44-4709-a9b6-9d8d2d568265'),
       ('c07cd7cb-ca44-4709-a9b6-9d8d2d568266', 'c07cd7cb-ce44-4709-a9b6-9d8d2d568265'),
       ('c07cd7cb-ce44-4709-a9b6-9d8d2d568263', 'c07cd7cb-ce44-4709-a9b6-9d8d2d568264'),
       ('c07cd7cb-ca44-4709-a9b6-9d8d2d568266', 'c07cd7cb-ce44-4709-a9b6-9d8d2d568264'),
       ('c07cd7cb-ce44-4709-a9b6-9d8d2d568263', 'c07cd7cb-ce44-4709-a9b6-9d8d2d568265'),
       ('c07cd7cb-ca44-4709-a9b6-9d8d2d568266', 'c07cd7cb-ce44-4709-a9b6-9d8d2d568265'),
       ('aaaaaaaa-aaaa-aaaa-aaaa-aaaaaaaaaaaa', 'c07cd7cb-ce44-4709-a9b6-9d8d2d568264'),
       ('bbbbbbbb-bbbb-bbbb-bbbb-bbbbbbbbbbbb', 'c07cd7cb-ce44-4709-a9b6-9d8d2d568264'),
       ('cccccccc-cccc-cccc-cccc-cccccccccccc', 'c07cd7cb-ce44-4709-a9b6-9d8d2d568264'),
       ('eeeeeeee-eeee-eeee-eeee-eeeeeeeeeeee', 'c07cd7cb-ce44-4709-a9b6-9d8d2d568264'),
       ('ffffffff-ffff-ffff-ffff-ffffffffffff', 'c07cd7cb-ce44-4709-a9b6-9d8d2d568264'),
       ('dddddddd-dddd-dddd-dddd-dddddddddddd', 'c07cd7cb-ce44-4709-a9b6-9d8d2d568264')
;
