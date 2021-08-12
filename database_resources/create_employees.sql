-- public.employees definition

-- Drop table

DROP TABLE public.employees;

CREATE TABLE public.employees (
	username varchar NOT NULL,
	"password" bytea NOT NULL,
	id serial NOT NULL,
	CONSTRAINT employees_pk PRIMARY KEY (id)
);