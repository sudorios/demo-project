--
-- PostgreSQL database dump
--

-- Dumped from database version 17.5
-- Dumped by pg_dump version 17.5

-- Started on 2025-09-25 10:11:18

SET statement_timeout = 0;
SET lock_timeout = 0;
SET idle_in_transaction_session_timeout = 0;
SET transaction_timeout = 0;
SET client_encoding = 'UTF8';
SET standard_conforming_strings = on;
SELECT pg_catalog.set_config('search_path', '', false);
SET check_function_bodies = false;
SET xmloption = content;
SET client_min_messages = warning;
SET row_security = off;

--
-- TOC entry 863 (class 1247 OID 106761)
-- Name: estado_proyecto; Type: TYPE; Schema: public; Owner: developer
--

CREATE TYPE public.estado_proyecto AS ENUM (
    'PLANIFICACIÓN',
    'EN CURSO',
    'EN REVISIÓN',
    'FINALIZADO'
);


ALTER TYPE public.estado_proyecto OWNER TO developer;

--
-- TOC entry 866 (class 1247 OID 106793)
-- Name: tipo_permiso; Type: TYPE; Schema: public; Owner: developer
--

CREATE TYPE public.tipo_permiso AS ENUM (
    'LECTURA',
    'EDICION'
);


ALTER TYPE public.tipo_permiso OWNER TO developer;

--
-- TOC entry 234 (class 1255 OID 106951)
-- Name: update_updated_at_column(); Type: FUNCTION; Schema: public; Owner: developer
--

CREATE FUNCTION public.update_updated_at_column() RETURNS trigger
    LANGUAGE plpgsql
    AS $$
BEGIN
    NEW.updated_at = CURRENT_TIMESTAMP;
    RETURN NEW;
END;
$$;


ALTER FUNCTION public.update_updated_at_column() OWNER TO developer;

SET default_tablespace = '';

SET default_table_access_method = heap;

--
-- TOC entry 218 (class 1259 OID 106846)
-- Name: project_status; Type: TABLE; Schema: public; Owner: developer
--

CREATE TABLE public.project_status (
    id integer NOT NULL,
    code character varying(20) NOT NULL,
    name character varying(50) NOT NULL,
    description text,
    created_at timestamp without time zone DEFAULT CURRENT_TIMESTAMP
);


ALTER TABLE public.project_status OWNER TO developer;

--
-- TOC entry 226 (class 1259 OID 106897)
-- Name: projects; Type: TABLE; Schema: public; Owner: developer
--

CREATE TABLE public.projects (
    id integer NOT NULL,
    project_code character varying(20) NOT NULL,
    name character varying(200) NOT NULL,
    description text,
    emoji "char",
    start_date date,
    end_date date,
    status_id integer DEFAULT 1 NOT NULL,
    category_id integer,
    user_id integer NOT NULL,
    created_at timestamp without time zone DEFAULT CURRENT_TIMESTAMP,
    updated_at timestamp without time zone DEFAULT CURRENT_TIMESTAMP
);


ALTER TABLE public.projects OWNER TO developer;

--
-- TOC entry 229 (class 1259 OID 106986)
-- Name: board_view; Type: VIEW; Schema: public; Owner: developer
--

CREATE VIEW public.board_view AS
 SELECT p.id,
    p.name,
    p.project_code,
    p.emoji,
    p.user_id,
    ps.name AS status_name
   FROM (public.projects p
     LEFT JOIN public.project_status ps ON ((p.status_id = ps.id)))
  ORDER BY p.id;


ALTER VIEW public.board_view OWNER TO developer;

--
-- TOC entry 231 (class 1259 OID 106991)
-- Name: notification; Type: TABLE; Schema: public; Owner: developer
--

CREATE TABLE public.notification (
    id bigint NOT NULL,
    user_id integer NOT NULL,
    title character varying(150) NOT NULL,
    message text NOT NULL,
    is_read boolean DEFAULT false,
    created_at timestamp without time zone DEFAULT CURRENT_TIMESTAMP
);


ALTER TABLE public.notification OWNER TO developer;

--
-- TOC entry 230 (class 1259 OID 106990)
-- Name: notification_id_seq; Type: SEQUENCE; Schema: public; Owner: developer
--

CREATE SEQUENCE public.notification_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER SEQUENCE public.notification_id_seq OWNER TO developer;

--
-- TOC entry 5025 (class 0 OID 0)
-- Dependencies: 230
-- Name: notification_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: developer
--

ALTER SEQUENCE public.notification_id_seq OWNED BY public.notification.id;


--
-- TOC entry 220 (class 1259 OID 106858)
-- Name: permission_types; Type: TABLE; Schema: public; Owner: developer
--

CREATE TABLE public.permission_types (
    id integer NOT NULL,
    code character varying(20) NOT NULL,
    name character varying(50) NOT NULL,
    description text,
    created_at timestamp without time zone DEFAULT CURRENT_TIMESTAMP
);


ALTER TABLE public.permission_types OWNER TO developer;

--
-- TOC entry 219 (class 1259 OID 106857)
-- Name: permission_types_id_seq; Type: SEQUENCE; Schema: public; Owner: developer
--

CREATE SEQUENCE public.permission_types_id_seq
    AS integer
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER SEQUENCE public.permission_types_id_seq OWNER TO developer;

--
-- TOC entry 5026 (class 0 OID 0)
-- Dependencies: 219
-- Name: permission_types_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: developer
--

ALTER SEQUENCE public.permission_types_id_seq OWNED BY public.permission_types.id;


--
-- TOC entry 222 (class 1259 OID 106870)
-- Name: project_categories; Type: TABLE; Schema: public; Owner: developer
--

CREATE TABLE public.project_categories (
    id integer NOT NULL,
    code character varying(20) NOT NULL,
    name character varying(50) NOT NULL,
    description text,
    created_at timestamp without time zone DEFAULT CURRENT_TIMESTAMP
);


ALTER TABLE public.project_categories OWNER TO developer;

--
-- TOC entry 221 (class 1259 OID 106869)
-- Name: project_categories_id_seq; Type: SEQUENCE; Schema: public; Owner: developer
--

CREATE SEQUENCE public.project_categories_id_seq
    AS integer
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER SEQUENCE public.project_categories_id_seq OWNER TO developer;

--
-- TOC entry 5027 (class 0 OID 0)
-- Dependencies: 221
-- Name: project_categories_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: developer
--

ALTER SEQUENCE public.project_categories_id_seq OWNED BY public.project_categories.id;


--
-- TOC entry 217 (class 1259 OID 106845)
-- Name: project_status_id_seq; Type: SEQUENCE; Schema: public; Owner: developer
--

CREATE SEQUENCE public.project_status_id_seq
    AS integer
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER SEQUENCE public.project_status_id_seq OWNER TO developer;

--
-- TOC entry 5028 (class 0 OID 0)
-- Dependencies: 217
-- Name: project_status_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: developer
--

ALTER SEQUENCE public.project_status_id_seq OWNED BY public.project_status.id;


--
-- TOC entry 225 (class 1259 OID 106896)
-- Name: projects_id_seq; Type: SEQUENCE; Schema: public; Owner: developer
--

CREATE SEQUENCE public.projects_id_seq
    AS integer
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER SEQUENCE public.projects_id_seq OWNER TO developer;

--
-- TOC entry 5029 (class 0 OID 0)
-- Dependencies: 225
-- Name: projects_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: developer
--

ALTER SEQUENCE public.projects_id_seq OWNED BY public.projects.id;


--
-- TOC entry 233 (class 1259 OID 107007)
-- Name: roles; Type: TABLE; Schema: public; Owner: developer
--

CREATE TABLE public.roles (
    id integer NOT NULL,
    name character varying(50) NOT NULL,
    description text
);


ALTER TABLE public.roles OWNER TO developer;

--
-- TOC entry 232 (class 1259 OID 107006)
-- Name: roles_id_seq; Type: SEQUENCE; Schema: public; Owner: developer
--

CREATE SEQUENCE public.roles_id_seq
    AS integer
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER SEQUENCE public.roles_id_seq OWNER TO developer;

--
-- TOC entry 5030 (class 0 OID 0)
-- Dependencies: 232
-- Name: roles_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: developer
--

ALTER SEQUENCE public.roles_id_seq OWNED BY public.roles.id;


--
-- TOC entry 228 (class 1259 OID 106926)
-- Name: shared_projects; Type: TABLE; Schema: public; Owner: developer
--

CREATE TABLE public.shared_projects (
    id integer NOT NULL,
    project_id integer NOT NULL,
    user_id integer NOT NULL,
    permission_type_id integer DEFAULT 1 NOT NULL,
    created_at timestamp without time zone DEFAULT CURRENT_TIMESTAMP,
    role_id integer
);


ALTER TABLE public.shared_projects OWNER TO developer;

--
-- TOC entry 227 (class 1259 OID 106925)
-- Name: shared_projects_id_seq; Type: SEQUENCE; Schema: public; Owner: developer
--

CREATE SEQUENCE public.shared_projects_id_seq
    AS integer
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER SEQUENCE public.shared_projects_id_seq OWNER TO developer;

--
-- TOC entry 5031 (class 0 OID 0)
-- Dependencies: 227
-- Name: shared_projects_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: developer
--

ALTER SEQUENCE public.shared_projects_id_seq OWNED BY public.shared_projects.id;


--
-- TOC entry 224 (class 1259 OID 106882)
-- Name: user; Type: TABLE; Schema: public; Owner: developer
--

CREATE TABLE public."user" (
    id integer NOT NULL,
    username character varying(50) NOT NULL,
    email character varying(100) NOT NULL,
    password_hash character varying(255) NOT NULL,
    first_name character varying(100),
    last_name character varying(100),
    company_name character varying(100),
    "position" character varying(100),
    phone character varying(20),
    profile_image_url character varying(500),
    created_at timestamp without time zone DEFAULT CURRENT_TIMESTAMP,
    updated_at timestamp without time zone DEFAULT CURRENT_TIMESTAMP
);


ALTER TABLE public."user" OWNER TO developer;

--
-- TOC entry 223 (class 1259 OID 106881)
-- Name: users_id_seq; Type: SEQUENCE; Schema: public; Owner: developer
--

CREATE SEQUENCE public.users_id_seq
    AS integer
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER SEQUENCE public.users_id_seq OWNER TO developer;

--
-- TOC entry 5032 (class 0 OID 0)
-- Dependencies: 223
-- Name: users_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: developer
--

ALTER SEQUENCE public.users_id_seq OWNED BY public."user".id;


--
-- TOC entry 4804 (class 2604 OID 106994)
-- Name: notification id; Type: DEFAULT; Schema: public; Owner: developer
--

ALTER TABLE ONLY public.notification ALTER COLUMN id SET DEFAULT nextval('public.notification_id_seq'::regclass);


--
-- TOC entry 4790 (class 2604 OID 106861)
-- Name: permission_types id; Type: DEFAULT; Schema: public; Owner: developer
--

ALTER TABLE ONLY public.permission_types ALTER COLUMN id SET DEFAULT nextval('public.permission_types_id_seq'::regclass);


--
-- TOC entry 4792 (class 2604 OID 106873)
-- Name: project_categories id; Type: DEFAULT; Schema: public; Owner: developer
--

ALTER TABLE ONLY public.project_categories ALTER COLUMN id SET DEFAULT nextval('public.project_categories_id_seq'::regclass);


--
-- TOC entry 4788 (class 2604 OID 106849)
-- Name: project_status id; Type: DEFAULT; Schema: public; Owner: developer
--

ALTER TABLE ONLY public.project_status ALTER COLUMN id SET DEFAULT nextval('public.project_status_id_seq'::regclass);


--
-- TOC entry 4797 (class 2604 OID 106900)
-- Name: projects id; Type: DEFAULT; Schema: public; Owner: developer
--

ALTER TABLE ONLY public.projects ALTER COLUMN id SET DEFAULT nextval('public.projects_id_seq'::regclass);


--
-- TOC entry 4807 (class 2604 OID 107010)
-- Name: roles id; Type: DEFAULT; Schema: public; Owner: developer
--

ALTER TABLE ONLY public.roles ALTER COLUMN id SET DEFAULT nextval('public.roles_id_seq'::regclass);


--
-- TOC entry 4801 (class 2604 OID 106929)
-- Name: shared_projects id; Type: DEFAULT; Schema: public; Owner: developer
--

ALTER TABLE ONLY public.shared_projects ALTER COLUMN id SET DEFAULT nextval('public.shared_projects_id_seq'::regclass);


--
-- TOC entry 4794 (class 2604 OID 106885)
-- Name: user id; Type: DEFAULT; Schema: public; Owner: developer
--

ALTER TABLE ONLY public."user" ALTER COLUMN id SET DEFAULT nextval('public.users_id_seq'::regclass);


--
-- TOC entry 5017 (class 0 OID 106991)
-- Dependencies: 231
-- Data for Name: notification; Type: TABLE DATA; Schema: public; Owner: developer
--

COPY public.notification (id, user_id, title, message, is_read, created_at) FROM stdin;
1	1	Nuevo Proyecto Asignado	Nuevo Proyecto	t	2025-09-24 23:05:59.412382
\.


--
-- TOC entry 5007 (class 0 OID 106858)
-- Dependencies: 220
-- Data for Name: permission_types; Type: TABLE DATA; Schema: public; Owner: developer
--

COPY public.permission_types (id, code, name, description, created_at) FROM stdin;
1	LECTURA	Lectura	Permiso de solo lectura	2025-09-22 21:30:57.029643
2	EDICION	Edición	Permiso de lectura y edición	2025-09-22 21:30:57.029643
4	READ	Read	Permiso de solo lectura	2025-09-24 23:44:09.763836
5	EDIT	Edit	Permiso de edición	2025-09-24 23:44:09.763836
6	ADMIN	Admin	Permiso de administración total	2025-09-24 23:44:09.763836
\.


--
-- TOC entry 5009 (class 0 OID 106870)
-- Dependencies: 222
-- Data for Name: project_categories; Type: TABLE DATA; Schema: public; Owner: developer
--

COPY public.project_categories (id, code, name, description, created_at) FROM stdin;
1	APP	Desarrollo de aplicaciones	Proyectos relacionados al desarrollo de aplicaciones	2025-09-22 21:30:57.029643
2	RRSS	Diseño de redes sociales	Proyectos de diseño para redes sociales	2025-09-22 21:30:57.029643
3	SOFTWARE	Programación	Proyectos de programación y desarrollo de software	2025-09-22 21:30:57.029643
4	CALIDAD	Control de calidad	Proyectos orientados al control y aseguramiento de calidad	2025-09-22 21:30:57.029643
5	PAGOS	Sistemas de pagos	Proyectos de sistemas de pago	2025-09-22 21:30:57.029643
6	NOTIFICACIONES	Notificaciones	Proyectos de sistemas de notificación	2025-09-22 21:30:57.029643
\.


--
-- TOC entry 5005 (class 0 OID 106846)
-- Dependencies: 218
-- Data for Name: project_status; Type: TABLE DATA; Schema: public; Owner: developer
--

COPY public.project_status (id, code, name, description, created_at) FROM stdin;
1	PLANIFICACION	Planificación	Proyecto en fase de planificación	2025-09-22 21:30:57.029643
2	EN_CURSO	En curso	Proyecto actualmente en desarrollo	2025-09-22 21:30:57.029643
3	REVISION	En revisión	Proyecto en proceso de revisión	2025-09-22 21:30:57.029643
4	FINALIZADO	Finalizado	Proyecto terminado y cerrado	2025-09-22 21:30:57.029643
\.


--
-- TOC entry 5013 (class 0 OID 106897)
-- Dependencies: 226
-- Data for Name: projects; Type: TABLE DATA; Schema: public; Owner: developer
--

COPY public.projects (id, project_code, name, description, emoji, start_date, end_date, status_id, category_id, user_id, created_at, updated_at) FROM stdin;
3	PROY-003	Módulos de programación	Desarrollo de funcionalidades clave	\N	\N	\N	3	3	1	2025-09-22 21:30:57.029643	2025-09-22 21:30:57.029643
4	PROY-004	Control de calidad	Aseguramiento de calidad del producto	\N	\N	\N	4	4	1	2025-09-22 21:30:57.029643	2025-09-22 21:30:57.029643
5	PROY-005	Sistema de notificaciones	Implementación de alertas y notificaciones	\N	\N	\N	1	6	1	2025-09-22 21:30:57.029643	2025-09-22 21:30:57.029643
6	PROY-006	Sistema de pagos	Plataforma para gestión de pagos	\N	\N	\N	3	5	1	2025-09-22 21:30:57.029643	2025-09-22 21:30:57.029643
2	PROY-002	Diseño de RR.SS.	Diseño para redes sociales de marca	\\360	\N	\N	2	2	1	2025-09-22 21:30:57.029643	2025-09-24 12:12:39.735601
8	PRJ-AD65F8	Proyecto Demo	Proyecto de prueba con categoría	\\360	2025-09-24	2025-12-31	2	3	1	2025-09-24 14:49:38.011155	2025-09-24 14:49:38.011155
9	PRJ-D9BC85	Proyecto Ejemplo	Proyecto de prueba	\\360	2025-09-24	2025-12-31	2	3	1	2025-09-25 09:34:19.703984	2025-09-25 09:34:19.703984
1	PROY-001	Ejemplo de Proyecto	Proyecto de aplicación móvil	\N	\N	\N	1	1	1	2025-09-22 21:30:57.029643	2025-09-25 10:09:59.799184
\.


--
-- TOC entry 5019 (class 0 OID 107007)
-- Dependencies: 233
-- Data for Name: roles; Type: TABLE DATA; Schema: public; Owner: developer
--

COPY public.roles (id, name, description) FROM stdin;
1	OWNER	Dueño del proyecto
2	COLLABORATOR	Colaborador con permisos
3	VIEWER	Solo lectura
\.


--
-- TOC entry 5015 (class 0 OID 106926)
-- Dependencies: 228
-- Data for Name: shared_projects; Type: TABLE DATA; Schema: public; Owner: developer
--

COPY public.shared_projects (id, project_id, user_id, permission_type_id, created_at, role_id) FROM stdin;
5	2	4	5	2025-09-24 23:48:46.387039	2
8	1	4	5	2025-09-24 23:57:40.087393	2
10	4	4	5	2025-09-25 09:50:18.452572	2
\.


--
-- TOC entry 5011 (class 0 OID 106882)
-- Dependencies: 224
-- Data for Name: user; Type: TABLE DATA; Schema: public; Owner: developer
--

COPY public."user" (id, username, email, password_hash, first_name, last_name, company_name, "position", phone, profile_image_url, created_at, updated_at) FROM stdin;
1	migueliberato	miguelliberato@gmail.com	$2a$10$bd0b1u12afLJerhyelSui.RbGfz3DLCSw1FlBtKWaODHiJCUmGKMa	Miguel Angel	Liberato Carmin	LVL Consulting	CEO LVL Consulting	+51 987654321	\N	2025-09-22 21:30:57.029643	2025-09-25 08:45:23.138236
2	rios	rios@example.com	$2a$10$x7TGJs9MRkBqx9wrrdScPuI/3KC1DD3MMj7eelyA7rDRBqvFEwvXu	Rios	Gonzales	Ejemplo	Senior Developer	987654321	https://i.pravatar.cc/150?img=3	2025-09-22 22:30:44.309	2025-09-25 10:09:30.9605
4	dan	dan@example.com	$2a$10$GzdaNWcZLtOP3C7wUizSw.1njQCQkvJcDCbCowitfTNk/Yv7Ny8ry	dan	Gonzalez	Ejemplo2	Developer	987654321	\N	2025-09-22 22:38:44.852	2025-09-25 10:09:30.9605
\.


--
-- TOC entry 5033 (class 0 OID 0)
-- Dependencies: 230
-- Name: notification_id_seq; Type: SEQUENCE SET; Schema: public; Owner: developer
--

SELECT pg_catalog.setval('public.notification_id_seq', 1, false);


--
-- TOC entry 5034 (class 0 OID 0)
-- Dependencies: 219
-- Name: permission_types_id_seq; Type: SEQUENCE SET; Schema: public; Owner: developer
--

SELECT pg_catalog.setval('public.permission_types_id_seq', 6, true);


--
-- TOC entry 5035 (class 0 OID 0)
-- Dependencies: 221
-- Name: project_categories_id_seq; Type: SEQUENCE SET; Schema: public; Owner: developer
--

SELECT pg_catalog.setval('public.project_categories_id_seq', 6, true);


--
-- TOC entry 5036 (class 0 OID 0)
-- Dependencies: 217
-- Name: project_status_id_seq; Type: SEQUENCE SET; Schema: public; Owner: developer
--

SELECT pg_catalog.setval('public.project_status_id_seq', 4, true);


--
-- TOC entry 5037 (class 0 OID 0)
-- Dependencies: 225
-- Name: projects_id_seq; Type: SEQUENCE SET; Schema: public; Owner: developer
--

SELECT pg_catalog.setval('public.projects_id_seq', 9, true);


--
-- TOC entry 5038 (class 0 OID 0)
-- Dependencies: 232
-- Name: roles_id_seq; Type: SEQUENCE SET; Schema: public; Owner: developer
--

SELECT pg_catalog.setval('public.roles_id_seq', 1, false);


--
-- TOC entry 5039 (class 0 OID 0)
-- Dependencies: 227
-- Name: shared_projects_id_seq; Type: SEQUENCE SET; Schema: public; Owner: developer
--

SELECT pg_catalog.setval('public.shared_projects_id_seq', 10, true);


--
-- TOC entry 5040 (class 0 OID 0)
-- Dependencies: 223
-- Name: users_id_seq; Type: SEQUENCE SET; Schema: public; Owner: developer
--

SELECT pg_catalog.setval('public.users_id_seq', 4, true);


--
-- TOC entry 4843 (class 2606 OID 107000)
-- Name: notification notification_pkey; Type: CONSTRAINT; Schema: public; Owner: developer
--

ALTER TABLE ONLY public.notification
    ADD CONSTRAINT notification_pkey PRIMARY KEY (id);


--
-- TOC entry 4813 (class 2606 OID 106868)
-- Name: permission_types permission_types_code_key; Type: CONSTRAINT; Schema: public; Owner: developer
--

ALTER TABLE ONLY public.permission_types
    ADD CONSTRAINT permission_types_code_key UNIQUE (code);


--
-- TOC entry 4815 (class 2606 OID 106866)
-- Name: permission_types permission_types_pkey; Type: CONSTRAINT; Schema: public; Owner: developer
--

ALTER TABLE ONLY public.permission_types
    ADD CONSTRAINT permission_types_pkey PRIMARY KEY (id);


--
-- TOC entry 4817 (class 2606 OID 106880)
-- Name: project_categories project_categories_code_key; Type: CONSTRAINT; Schema: public; Owner: developer
--

ALTER TABLE ONLY public.project_categories
    ADD CONSTRAINT project_categories_code_key UNIQUE (code);


--
-- TOC entry 4819 (class 2606 OID 106878)
-- Name: project_categories project_categories_pkey; Type: CONSTRAINT; Schema: public; Owner: developer
--

ALTER TABLE ONLY public.project_categories
    ADD CONSTRAINT project_categories_pkey PRIMARY KEY (id);


--
-- TOC entry 4809 (class 2606 OID 106856)
-- Name: project_status project_status_code_key; Type: CONSTRAINT; Schema: public; Owner: developer
--

ALTER TABLE ONLY public.project_status
    ADD CONSTRAINT project_status_code_key UNIQUE (code);


--
-- TOC entry 4811 (class 2606 OID 106854)
-- Name: project_status project_status_pkey; Type: CONSTRAINT; Schema: public; Owner: developer
--

ALTER TABLE ONLY public.project_status
    ADD CONSTRAINT project_status_pkey PRIMARY KEY (id);


--
-- TOC entry 4833 (class 2606 OID 106907)
-- Name: projects projects_pkey; Type: CONSTRAINT; Schema: public; Owner: developer
--

ALTER TABLE ONLY public.projects
    ADD CONSTRAINT projects_pkey PRIMARY KEY (id);


--
-- TOC entry 4835 (class 2606 OID 106909)
-- Name: projects projects_project_code_key; Type: CONSTRAINT; Schema: public; Owner: developer
--

ALTER TABLE ONLY public.projects
    ADD CONSTRAINT projects_project_code_key UNIQUE (project_code);


--
-- TOC entry 4845 (class 2606 OID 107016)
-- Name: roles roles_name_key; Type: CONSTRAINT; Schema: public; Owner: developer
--

ALTER TABLE ONLY public.roles
    ADD CONSTRAINT roles_name_key UNIQUE (name);


--
-- TOC entry 4847 (class 2606 OID 107014)
-- Name: roles roles_pkey; Type: CONSTRAINT; Schema: public; Owner: developer
--

ALTER TABLE ONLY public.roles
    ADD CONSTRAINT roles_pkey PRIMARY KEY (id);


--
-- TOC entry 4839 (class 2606 OID 106933)
-- Name: shared_projects shared_projects_pkey; Type: CONSTRAINT; Schema: public; Owner: developer
--

ALTER TABLE ONLY public.shared_projects
    ADD CONSTRAINT shared_projects_pkey PRIMARY KEY (id);


--
-- TOC entry 4841 (class 2606 OID 106935)
-- Name: shared_projects shared_projects_project_id_user_id_key; Type: CONSTRAINT; Schema: public; Owner: developer
--

ALTER TABLE ONLY public.shared_projects
    ADD CONSTRAINT shared_projects_project_id_user_id_key UNIQUE (project_id, user_id);


--
-- TOC entry 4823 (class 2606 OID 106895)
-- Name: user users_email_key; Type: CONSTRAINT; Schema: public; Owner: developer
--

ALTER TABLE ONLY public."user"
    ADD CONSTRAINT users_email_key UNIQUE (email);


--
-- TOC entry 4825 (class 2606 OID 106891)
-- Name: user users_pkey; Type: CONSTRAINT; Schema: public; Owner: developer
--

ALTER TABLE ONLY public."user"
    ADD CONSTRAINT users_pkey PRIMARY KEY (id);


--
-- TOC entry 4827 (class 2606 OID 106893)
-- Name: user users_username_key; Type: CONSTRAINT; Schema: public; Owner: developer
--

ALTER TABLE ONLY public."user"
    ADD CONSTRAINT users_username_key UNIQUE (username);


--
-- TOC entry 4828 (class 1259 OID 106959)
-- Name: idx_projects_category; Type: INDEX; Schema: public; Owner: developer
--

CREATE INDEX idx_projects_category ON public.projects USING btree (category_id);


--
-- TOC entry 4829 (class 1259 OID 106956)
-- Name: idx_projects_project_code; Type: INDEX; Schema: public; Owner: developer
--

CREATE INDEX idx_projects_project_code ON public.projects USING btree (project_code);


--
-- TOC entry 4830 (class 1259 OID 106958)
-- Name: idx_projects_status; Type: INDEX; Schema: public; Owner: developer
--

CREATE INDEX idx_projects_status ON public.projects USING btree (status_id);


--
-- TOC entry 4831 (class 1259 OID 106957)
-- Name: idx_projects_user_id; Type: INDEX; Schema: public; Owner: developer
--

CREATE INDEX idx_projects_user_id ON public.projects USING btree (user_id);


--
-- TOC entry 4836 (class 1259 OID 106960)
-- Name: idx_shared_projects_project; Type: INDEX; Schema: public; Owner: developer
--

CREATE INDEX idx_shared_projects_project ON public.shared_projects USING btree (project_id);


--
-- TOC entry 4837 (class 1259 OID 106961)
-- Name: idx_shared_projects_user; Type: INDEX; Schema: public; Owner: developer
--

CREATE INDEX idx_shared_projects_user ON public.shared_projects USING btree (user_id);


--
-- TOC entry 4820 (class 1259 OID 106954)
-- Name: idx_users_email; Type: INDEX; Schema: public; Owner: developer
--

CREATE INDEX idx_users_email ON public."user" USING btree (email);


--
-- TOC entry 4821 (class 1259 OID 106955)
-- Name: idx_users_username; Type: INDEX; Schema: public; Owner: developer
--

CREATE INDEX idx_users_username ON public."user" USING btree (username);


--
-- TOC entry 4857 (class 2620 OID 106953)
-- Name: projects update_projects_updated_at; Type: TRIGGER; Schema: public; Owner: developer
--

CREATE TRIGGER update_projects_updated_at BEFORE UPDATE ON public.projects FOR EACH ROW EXECUTE FUNCTION public.update_updated_at_column();


--
-- TOC entry 4856 (class 2620 OID 106952)
-- Name: user update_users_updated_at; Type: TRIGGER; Schema: public; Owner: developer
--

CREATE TRIGGER update_users_updated_at BEFORE UPDATE ON public."user" FOR EACH ROW EXECUTE FUNCTION public.update_updated_at_column();


--
-- TOC entry 4855 (class 2606 OID 107001)
-- Name: notification fk_user; Type: FK CONSTRAINT; Schema: public; Owner: developer
--

ALTER TABLE ONLY public.notification
    ADD CONSTRAINT fk_user FOREIGN KEY (user_id) REFERENCES public."user"(id) ON DELETE CASCADE;


--
-- TOC entry 4848 (class 2606 OID 106915)
-- Name: projects projects_category_id_fkey; Type: FK CONSTRAINT; Schema: public; Owner: developer
--

ALTER TABLE ONLY public.projects
    ADD CONSTRAINT projects_category_id_fkey FOREIGN KEY (category_id) REFERENCES public.project_categories(id);


--
-- TOC entry 4849 (class 2606 OID 106910)
-- Name: projects projects_status_id_fkey; Type: FK CONSTRAINT; Schema: public; Owner: developer
--

ALTER TABLE ONLY public.projects
    ADD CONSTRAINT projects_status_id_fkey FOREIGN KEY (status_id) REFERENCES public.project_status(id);


--
-- TOC entry 4850 (class 2606 OID 106920)
-- Name: projects projects_user_id_fkey; Type: FK CONSTRAINT; Schema: public; Owner: developer
--

ALTER TABLE ONLY public.projects
    ADD CONSTRAINT projects_user_id_fkey FOREIGN KEY (user_id) REFERENCES public."user"(id) ON DELETE CASCADE;


--
-- TOC entry 4851 (class 2606 OID 106946)
-- Name: shared_projects shared_projects_permission_type_id_fkey; Type: FK CONSTRAINT; Schema: public; Owner: developer
--

ALTER TABLE ONLY public.shared_projects
    ADD CONSTRAINT shared_projects_permission_type_id_fkey FOREIGN KEY (permission_type_id) REFERENCES public.permission_types(id);


--
-- TOC entry 4852 (class 2606 OID 106936)
-- Name: shared_projects shared_projects_project_id_fkey; Type: FK CONSTRAINT; Schema: public; Owner: developer
--

ALTER TABLE ONLY public.shared_projects
    ADD CONSTRAINT shared_projects_project_id_fkey FOREIGN KEY (project_id) REFERENCES public.projects(id) ON DELETE CASCADE;


--
-- TOC entry 4853 (class 2606 OID 107036)
-- Name: shared_projects shared_projects_role_id_fkey; Type: FK CONSTRAINT; Schema: public; Owner: developer
--

ALTER TABLE ONLY public.shared_projects
    ADD CONSTRAINT shared_projects_role_id_fkey FOREIGN KEY (role_id) REFERENCES public.roles(id);


--
-- TOC entry 4854 (class 2606 OID 106941)
-- Name: shared_projects shared_projects_user_id_fkey; Type: FK CONSTRAINT; Schema: public; Owner: developer
--

ALTER TABLE ONLY public.shared_projects
    ADD CONSTRAINT shared_projects_user_id_fkey FOREIGN KEY (user_id) REFERENCES public."user"(id) ON DELETE CASCADE;


-- Completed on 2025-09-25 10:11:18

--
-- PostgreSQL database dump complete
--

