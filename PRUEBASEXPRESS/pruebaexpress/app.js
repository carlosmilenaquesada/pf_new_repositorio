const express = require('express');
const bodyParser = require('body-parser');
const mysql = require('mysql');

const app = express();

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



app.get('/sync/articles', (req, res) => {
    const sql = 'SELECT A.article_id, A.article_name, A.article_category_id, A.sale_base_price, A.vat_id, A.offer_start_date, A.offer_end_date, A.offer_sale_base_price FROM articles';

    connection.query(sql, (err, result) => {
        if (err) {
            console.error('Error al ejecutar la consulta:', err);
            res.status(500).json({ error: 'Error al obtener la información de artículos' });
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


const puerto = 3000;
app.listen(puerto, () => {
    console.log(`Servidor en ejecución en http:localhost:${puerto}`);
});
