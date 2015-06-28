--
-- PostgreSQL database dump
--

SET client_encoding = 'UTF8';
SET standard_conforming_strings = off;
SET check_function_bodies = false;
SET client_min_messages = warning;
SET escape_string_warning = off;

--
-- Name: SCHEMA public; Type: COMMENT; Schema: -; Owner: postgres
--

COMMENT ON SCHEMA public IS 'Standard public schema';


--
-- Name: plpgsql; Type: PROCEDURAL LANGUAGE; Schema: -; Owner: postgres
--

CREATE PROCEDURAL LANGUAGE plpgsql;


SET search_path = public, pg_catalog;

--
-- Name: add_const(text); Type: FUNCTION; Schema: public; Owner: userszybkosci
--

CREATE FUNCTION add_const(text) RETURNS text
    AS $_$
DECLARE
    key ALIAS FOR $1;
    DECLARE output TEXT DEFAULT '';
BEGIN
    SELECT INTO output wartosc FROM stale WHERE klucz = key;
    IF NOT FOUND THEN
        RAISE EXCEPTION 'dostep do stalych';
    END IF;
    RETURN output;
END;
$_$
    LANGUAGE plpgsql;


ALTER FUNCTION public.add_const(text) OWNER TO userszybkosci;

--
-- Name: add_content(text); Type: FUNCTION; Schema: public; Owner: userszybkosci
--

CREATE FUNCTION add_content(text) RETURNS text
    AS $_$
DECLARE
    DECLARE output TEXT DEFAULT '';
    key ALIAS FOR $1;
BEGIN
    SELECT INTO output tresc FROM strona WHERE nazwa_pliku=key;
    IF NOT FOUND THEN
        return 'Podana strona nie istnieje.';
    END IF;
    RETURN output;
END;
$_$
    LANGUAGE plpgsql;


ALTER FUNCTION public.add_content(text) OWNER TO userszybkosci;

--
-- Name: add_content(text, text); Type: FUNCTION; Schema: public; Owner: userszybkosci
--

CREATE FUNCTION add_content(text, text) RETURNS text
    AS $_$
DECLARE
    DECLARE output TEXT DEFAULT '';
    klucz ALIAS FOR $1;
    co_zwrocic ALIAS FOR $2;
BEGIN
    SELECT INTO output co_zwrocic FROM strona WHERE nazwa_pliku=key;
    IF NOT FOUND THEN
        return 'Podana strona nie istnieje.';
    END IF;
    RETURN output;
END;
$_$
    LANGUAGE plpgsql;


ALTER FUNCTION public.add_content(text, text) OWNER TO userszybkosci;

--
-- Name: add_styles(); Type: FUNCTION; Schema: public; Owner: userszybkosci
--

CREATE FUNCTION add_styles() RETURNS text
    AS $$
DECLARE
    DECLARE record RECORD;
    DECLARE subrecord RECORD;
    DECLARE output TEXT DEFAULT '';
BEGIN
    -- petla po wszystkich tagach w arkuszy styli
    FOR record IN SELECT * FROM tag LOOP
        output:= output || record.nazwa || E' {\n'; -- poczatkowa klamerka
        -- petla po wszytkich atrybutach dla tagu
        FOR subrecord IN SELECT nazwa, wartosc FROM atrybut WHERE tag=record.id LOOP
            output:= output || subrecord.nazwa || ':' || subrecord.wartosc ||  E';\n';
        END LOOP;
        output:= output || E'}\n'; -- koncowa klamerka
    END LOOP;
    -- skoro jestesmy tuta to w zmiennej output mamy gotowy plik css 
    RETURN output;
END;
$$
    LANGUAGE plpgsql;


ALTER FUNCTION public.add_styles() OWNER TO userszybkosci;

--
-- Name: cgi_prolog(); Type: FUNCTION; Schema: public; Owner: userszybkosci
--

CREATE FUNCTION cgi_prolog() RETURNS text
    AS $$
DECLARE
BEGIN
    RETURN E'Content-Type: text/html\n\n';
END;
$$
    LANGUAGE plpgsql;


ALTER FUNCTION public.cgi_prolog() OWNER TO userszybkosci;

--
-- Name: index(); Type: FUNCTION; Schema: public; Owner: userszybkosci
--

CREATE FUNCTION "index"() RETURNS text
    AS $$
DECLARE
BEGIN
    RETURN  add_const('prolog') || 
            add_const('htmlstart') || 
            '<style>' || add_styles() ||  '</style>' ||
            add_content('ciekawe') ||
            add_const('htmlkoniec');
END;
$$
    LANGUAGE plpgsql;


ALTER FUNCTION public."index"() OWNER TO userszybkosci;

--
-- Name: index(text); Type: FUNCTION; Schema: public; Owner: userszybkosci
--

CREATE FUNCTION "index"(text) RETURNS text
    AS $_$
DECLARE
    nazwa_pliku ALIAS FOR $1;
    DECLARE output TEXT DEFAULT E'Content-Type: text/html\n\n';
BEGIN
    -- zadanie wyslania arkusza styli
    IF nazwa_pliku='style' THEN
        output = output || add_styles();
        RETURN output;
    END IF;
    output = output || '<html><link rel="stylesheet" href="main.cgi?style"/><body>' || 
             page_content(nazwa_pliku) ||
             '</html></body>';
    RETURN output;
END;
$_$
    LANGUAGE plpgsql;


ALTER FUNCTION public."index"(text) OWNER TO userszybkosci;

--
-- Name: page_content(text, text); Type: FUNCTION; Schema: public; Owner: userszybkosci
--

CREATE FUNCTION page_content(text, text) RETURNS text
    AS $_$
DECLARE
    DECLARE output TEXT DEFAULT '';
    klucz ALIAS FOR $1;
    DECLARE co_zwrocic TEXT;
BEGIN
    SELECT INTO output tytul, tresc FROM strona WHERE nazwa_pliku=klucz;
    IF NOT FOUND THEN
        return 'Podana strona nie istnieje.';
    END IF;
    output = '<h1>tytul: ' || tytul || '</h1><hr/>tresc: ' || tresc;
    RETURN output;
END;
$_$
    LANGUAGE plpgsql;


ALTER FUNCTION public.page_content(text, text) OWNER TO userszybkosci;

--
-- Name: page_content(text); Type: FUNCTION; Schema: public; Owner: userszybkosci
--

CREATE FUNCTION page_content(text) RETURNS text
    AS $_$
DECLARE
    DECLARE output TEXT DEFAULT '';
    klucz ALIAS FOR $1;
    DECLARE r RECORD;
BEGIN
    SELECT INTO r *  FROM strona WHERE nazwa_pliku=klucz;
    IF NOT FOUND THEN
        return 'Podana strona nie istnieje.';
    END IF;
    output = '<h1>tytul: ' || r.tytul || '</h1><hr/>tresc: ' || r.tresc;
    RETURN output;
END;
$_$
    LANGUAGE plpgsql;


ALTER FUNCTION public.page_content(text) OWNER TO userszybkosci;

--
-- Name: zapelniacz(integer); Type: FUNCTION; Schema: public; Owner: userszybkosci
--

CREATE FUNCTION zapelniacz(integer) RETURNS text
    AS $_$
DECLARE
    ile ALIAS FOR $1;
    skala integer = 200;
BEGIN
    --DELETE FROM tabelka;
    FOR i IN 0 .. ile LOOP
        INSERT INTO tabelka(a,b,c) VALUES( skala*random(), skala*random(), skala*random());
        INSERT INTO tabelka1(x,y) VALUES( skala*random(), skala*random() );
    END LOOP;
    RETURN 'OK!' || ile;
END;
$_$
    LANGUAGE plpgsql;


ALTER FUNCTION public.zapelniacz(integer) OWNER TO userszybkosci;

SET default_tablespace = '';

SET default_with_oids = false;

--
-- Name: atrybut; Type: TABLE; Schema: public; Owner: userszybkosci; Tablespace: 
--

CREATE TABLE atrybut (
    tag integer,
    nazwa text,
    wartosc text
);


ALTER TABLE public.atrybut OWNER TO userszybkosci;

--
-- Name: str_seq; Type: SEQUENCE; Schema: public; Owner: userszybkosci
--

CREATE SEQUENCE str_seq
    INCREMENT BY 1
    NO MAXVALUE
    NO MINVALUE
    CACHE 1;


ALTER TABLE public.str_seq OWNER TO userszybkosci;

--
-- Name: str_seq; Type: SEQUENCE SET; Schema: public; Owner: userszybkosci
--

SELECT pg_catalog.setval('str_seq', 2, true);


--
-- Name: strona; Type: TABLE; Schema: public; Owner: userszybkosci; Tablespace: 
--

CREATE TABLE strona (
    id integer NOT NULL,
    tresc text,
    nazwa_pliku text,
    tytul text
);


ALTER TABLE public.strona OWNER TO userszybkosci;

--
-- Name: tag; Type: TABLE; Schema: public; Owner: userszybkosci; Tablespace: 
--

CREATE TABLE tag (
    id integer NOT NULL,
    nazwa text
);


ALTER TABLE public.tag OWNER TO userszybkosci;

--
-- Name: tag_seq; Type: SEQUENCE; Schema: public; Owner: userszybkosci
--

CREATE SEQUENCE tag_seq
    INCREMENT BY 1
    NO MAXVALUE
    NO MINVALUE
    CACHE 1;


ALTER TABLE public.tag_seq OWNER TO userszybkosci;

--
-- Name: tag_seq; Type: SEQUENCE SET; Schema: public; Owner: userszybkosci
--

SELECT pg_catalog.setval('tag_seq', 7, true);


--
-- Data for Name: atrybut; Type: TABLE DATA; Schema: public; Owner: userszybkosci
--

INSERT INTO atrybut VALUES (6, 'background', 'black');
INSERT INTO atrybut VALUES (6, 'color', 'pink');
INSERT INTO atrybut VALUES (7, 'font-size', '18px');


--
-- Data for Name: strona; Type: TABLE DATA; Schema: public; Owner: userszybkosci
--

INSERT INTO strona VALUES (2, 'admin', 'fooo', 'bar');
INSERT INTO strona VALUES (1, '<p>stppp lklklklkl klklklklk klklklklk klklkklk klklkk lkklklk lkklk klklk klkklkkl klklkk klklk klklk klk</p><p>khkjhkjhkjhkhkhkhkjhkhkjh hjhhjh kjhkjh khkj</p>', 'ciekawe', 'ssss');


--
-- Data for Name: tag; Type: TABLE DATA; Schema: public; Owner: userszybkosci
--

INSERT INTO tag VALUES (6, 'body');
INSERT INTO tag VALUES (7, 'p');


--
-- Name: strona_pkey; Type: CONSTRAINT; Schema: public; Owner: userszybkosci; Tablespace: 
--

ALTER TABLE ONLY strona
    ADD CONSTRAINT strona_pkey PRIMARY KEY (id);


--
-- Name: tag_pkey; Type: CONSTRAINT; Schema: public; Owner: userszybkosci; Tablespace: 
--

ALTER TABLE ONLY tag
    ADD CONSTRAINT tag_pkey PRIMARY KEY (id);


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

