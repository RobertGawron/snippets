--
-- PostgreSQL database dump
--

SET client_encoding = 'UTF8';
SET standard_conforming_strings = off;
SET check_function_bodies = false;
SET client_min_messages = warning;
SET escape_string_warning = off;

SET search_path = public, pg_catalog;

SET default_tablespace = '';

SET default_with_oids = false;

--
-- Name: osoba; Type: TABLE; Schema: public; Owner: rgawron; Tablespace: 
--

CREATE TABLE osoba (
    id integer,
    imie character varying(32),
    nazwisko character varying(32),
    wiek integer DEFAULT 55
);


ALTER TABLE public.osoba OWNER TO rgawron;

--
-- Name: updatewieku(); Type: FUNCTION; Schema: public; Owner: rgawron
--

CREATE FUNCTION updatewieku() RETURNS integer
    AS $$
    BEGIN;
        UPDATE Osoba set wiek = wiek + 1;
    ROLLBACK;
    SELECT 1;
$$
    LANGUAGE sql;


ALTER FUNCTION public.updatewieku() OWNER TO rgawron;

--
-- Name: id_osoba; Type: SEQUENCE; Schema: public; Owner: rgawron
--

CREATE SEQUENCE id_osoba
    INCREMENT BY 1
    NO MAXVALUE
    NO MINVALUE
    CACHE 1;


ALTER TABLE public.id_osoba OWNER TO rgawron;

--
-- Name: id_osoba; Type: SEQUENCE SET; Schema: public; Owner: rgawron
--

SELECT pg_catalog.setval('id_osoba', 2, true);


--
-- Data for Name: osoba; Type: TABLE DATA; Schema: public; Owner: rgawron
--

COPY osoba (id, imie, nazwisko, wiek) FROM stdin;
1	Adam	Kowalski	58
2	Janusz	Michnik	58
\.


--
-- Name: public; Type: ACL; Schema: -; Owner: postgres
--

REVOKE ALL ON SCHEMA public FROM PUBLIC;
REVOKE ALL ON SCHEMA public FROM postgres;
GRANT ALL ON SCHEMA public TO postgres;
GRANT ALL ON SCHEMA public TO PUBLIC;


--
-- PostgreSQL database dump complete
--

