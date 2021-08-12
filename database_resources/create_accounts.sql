-- public.accounts definition

-- Drop table

DROP TABLE public.accounts;

CREATE TABLE public.accounts (
	account_id serial NOT NULL,
	balance float8 NOT NULL,
	owner_id int8 NOT NULL,
	CONSTRAINT accounts_pk PRIMARY KEY (account_id)
);


-- public.accounts foreign keys

ALTER TABLE public.accounts ADD CONSTRAINT accounts_fk FOREIGN KEY (owner_id) REFERENCES public.clients(id) ON DELETE CASCADE;