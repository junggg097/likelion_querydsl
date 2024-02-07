INSERT INTO shop (name,description)
VALUES
    ('Nisl Sem PC','Fusce aliquam, enim'),
    ('Lobortis Augue Incorporated','auctor, nunc nulla'),
    ('Eu Institute','egestas a, dui. Cras'),
    ('Risus Nulla Eget Associates','est. Mauris'),
    ('Elit Sed Corporation','quis diam luctus'),
    ('Lorem Lorem Inc.','est. Nunc laoreet'),
    ('In Scelerisque Foundation','Nunc'),
    ('Lobortis Risus Limited','dis parturient montes,'),
    ('Neque Nullam Ut LLP','Phasellus elit'),
    ('Tempus Lorem Fringilla LLP','quam, elementum at, egestas');

INSERT INTO item (name,description,shop_id,price,stock)
VALUES
    ('mouse','purus,',1,68707,22),
    ('monitor','ullamcorper',1,11034,80),
    ('keyboard','magnis dis parturient',1,37700,43),
    ('speaker','faucibus leo, in lobortis',1,58281,82),
    ('mouse','at pretium',2,61395,85),
    ('monitor','massa rutrum',2,53854,54),
    ('keyboard','vulputate dui, nec',2,10952,58),
    ('speaker','gravida sagittis.',2,18103,57),
    ('mouse','tellus. Nunc lectus',3,81846,36),
    ('monitor','sagittis augue,',3,23507,59);
INSERT INTO item (name,description,shop_id,price,stock)
VALUES
    ('keyboard','ultricies ornare, elit elit',3,25511,94),
    ('speaker','est ac',3,19597,57),
    ('mouse','ut nisi',4,13688,61),
    ('monitor','natoque penatibus et',4,62116,38),
    ('keyboard','vulputate mauris',4,25028,41),
    ('speaker','Quisque ac libero nec',4,22685,48),
    ('mouse','lobortis ultrices. Vivamus',5,32101,54),
    ('monitor','arcu. Morbi sit',5,56267,37),
    ('keyboard','Mauris vel',5,48496,58),
    ('speaker','in',5,70633,71);
INSERT INTO item (name,description,shop_id,price,stock)
VALUES
    ('mouse','neque. Nullam',6,32901,19),
    ('monitor','eu tellus. Phasellus',6,35059,44),
    ('keyboard','posuere cubilia Curae Phasellus',6,22529,71),
    ('speaker','Nunc commodo auctor',6,94930,68),
    ('mouse','risus. Donec egestas. Aliquam',7,16251,33),
    ('monitor','et malesuada fames',7,60813,67),
    ('keyboard','justo.',7,33390,69),
    ('speaker','sem mollis',7,56080,97),
    ('mouse','sit',8,19070,19),
    ('monitor','In at pede.',8,33048,71);
INSERT INTO item (name,description,shop_id,price,stock)
VALUES
    ('keyboard','vulputate, posuere vulputate, lacus.',8,45197,30),
    ('speaker','nulla ante,',8,96083,98),
    ('mouse','mauris. Morbi',9,70104,13),
    ('monitor','malesuada id, erat. Etiam',9,55592,46),
    ('keyboard','facilisis facilisis,',9,89730,32),
    ('speaker','posuere',9,71030,94),
    ('mouse','nec',10,26254,56),
    ('monitor','neque. Nullam ut',10,61081,58),
    ('keyboard','scelerisque neque.',10,77121,13),
    ('speaker','congue, elit sed',10,47105,24);

UPDATE item
SET price = (price / 1000) * 1000;