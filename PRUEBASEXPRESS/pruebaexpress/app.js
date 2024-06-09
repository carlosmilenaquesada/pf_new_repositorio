const express = require('express');
const mysql = require('mysql');
const bodyParser = require('body-parser');

const app = express();
const puerto = 3000;

app.use(bodyParser.json());

const connection = mysql.createConnection({
    host: 'localhost',
    user: 'root',
    password: '',
    database: 'tpv'
});

connection.connect((err) => {
    if (err) {
        console.error('Error al conectar a la base de datos:', err);
    } else {
        console.log('Conexión exitosa a la base de datos MySQL');
    }
});

//GET ##########################################################################

app.get('/sync/articles', (req, res) => {
    const sql = 'SELECT A.article_id, A.article_name, A.article_category_id, A.unit_sale_base_price, A.vat_id, A.offer_start_date, A.offer_end_date, A.offer_unit_sale_base_price FROM articles A';

    connection.query(sql, (err, result) => {
        if (err) {
            console.error('Error al ejecutar la consulta:', err);
            res.status(500).json({ error: 'Error al obtener la información de artículos' });
        } else {
            res.json(result);
        }
    });
});

app.get('/sync/articles_categories', (req, res) => {
    const sql = 'SELECT * FROM articles_categories';

    connection.query(sql, (err, result) => {
        if (err) {
            console.error('Error al ejecutar la consulta:', err);
            res.status(500).json({ error: 'Error al obtener la información de categorias de artículos' });
        } else {
            res.json(result);
        }
    });
});

app.get('/sync/articles_families', (req, res) => {
    const sql = 'SELECT * FROM articles_families';

    connection.query(sql, (err, result) => {
        if (err) {
            console.error('Error al ejecutar la consulta:', err);
            res.status(500).json({ error: 'Error al obtener la información de familias de artículos' });
        } else {
            res.json(result);
        }
    });
});

app.get('/sync/barcodes', (req, res) => {
    const sql = 'SELECT * FROM barcodes';

    connection.query(sql, (err, result) => {
        if (err) {
            console.error('Error al ejecutar la consulta:', err);
            res.status(500).json({ error: 'Error al obtener la información de códigos de barras' });
        } else {
            res.json(result);
        }
    });
});

app.get('/sync/customers_taxables', (req, res) => {
    const sql = 'SELECT * FROM customers_taxables';

    connection.query(sql, (err, result) => {
        if (err) {
            console.error('Error al ejecutar la consulta:', err);
            res.status(500).json({ error: 'Error al obtener la información de clientes' });
        } else {
            res.json(result);
        }
    });
});

app.get('/sync/customers_types', (req, res) => {
    const sql = 'SELECT * FROM customers_types';

    connection.query(sql, (err, result) => {
        if (err) {
            console.error('Error al ejecutar la consulta:', err);
            res.status(500).json({ error: 'Error al obtener la información de clientes' });
        } else {
            res.json(result);
        }
    });
});

app.get('/sync/payment_methods', (req, res) => {
    const sql = 'SELECT * FROM payment_methods';
    connection.query(sql, (err, result) => {
        if (err) {
            console.error('Error al ejecutar la consulta:', err);
            res.status(500).json({ error: 'Error al obtener la información de formas de pago' });
        } else {
            res.json(result);
        }
    });
});

app.get('/sync/users', (req, res) => {
    const sql = 'SELECT * FROM users';
    connection.query(sql, (err, result) => {
        if (err) {
            console.error('Error al ejecutar la consulta:', err);
            res.status(500).json({ error: 'Error al obtener la información de usuarios' });
        } else {
            res.json(result);
        }
    });
});

app.get('/sync/vats', (req, res) => {
    const sql = 'SELECT * FROM vats';

    connection.query(sql, (err, result) => {
        if (err) {
            console.error('Error al ejecutar la consulta:', err);
            res.status(500).json({ error: 'Error al obtener la información de IVA' });
        } else {
            res.json(result);
        }
    });
});



//POST #########################################################################

app.post('/add/tickets', (req, res) => {
    const tickets = req.body.tickets;  
    if (!Array.isArray(tickets)) {
        return res.status(400).send({ error: true, message: 'Datos de tickets incorrectos'});
    }

    if (tickets.length === 0) {
        return res.status(400).send({ error: true, message: 'No se proporcionaron tickets para insertar' });
    }
    const query = 'INSERT INTO tickets (ticket_id, sale_date, customer_tax_id, ticket_status_id, payment_method_id) VALUES ?';
    const values = tickets.map(ticket => [
        ticket.ticket_id,
        ticket.sale_date,
        ticket.customer_tax_id,
        ticket.ticket_status_id,
        ticket.payment_method_id
    ]);

    connection.query(query, [values], (err, result) => {
        if (err) {
          console.error('Error al insertar datos en la base de datos:', err);
          return res.status(500).send({ error: true, message: 'Error al insertar datos en la base de datos', details: err.message });
        }
        res.send({ error: false, data: result, message: 'Nuevos tickets agregados correctamente' });
    });
});


app.post('/add/tickets_lines', (req, res) => {
    const tickets_lines = req.body.tickets_lines;
    
    if (!Array.isArray(ticketsLines)) {
        return res.status(400).send({ error: true, message: 'Datos de líneas de tickets incorrectos'});
    }

    if (ticketsLines.length === 0) {
        return res.status(400).send({ error: true, message: 'No se proporcionaron líneas de tickets para insertar' });
    }

    const query = 'INSERT INTO tickets_lines (ticket_line_id, ticket_id, article_id, article_quantity, applicated_sale_base_price, vat_id, sold_during_offer) VALUES ?';
    const values = tickets_lines.map(ticket_line => [
        ticket_line.ticket_line_id,
        ticket_line.ticket_id,
        ticket_line.article_id,
        ticket_line.article_quantity,
        ticket_line.applicated_sale_base_price,
        ticket_line.vat_id,
        ticket_line.sold_during_offer
    ]);

    connection.query(query, [values], (err, result) => {
        if (err) {
          console.error('Error al insertar datos en la base de datos:', err);
          return res.status(500).send({ error: true, message: 'Error al insertar datos en la base de datos', details: err.message });
        }
        res.send({ error: false, data: result, message: 'Nuevas líneas de tickets agregadas correctamente' });
    });
});


app.listen(puerto, () => {
    console.log(`Servidor en ejecución en http:localhost:${puerto}`);
});
