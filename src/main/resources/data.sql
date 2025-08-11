INSERT INTO hobbies (name) VALUES
                               ('Yoga'), ('Painting'), ('Hiking'), ('Cooking'), ('Photography'),
                               ('Gardening'), ('Music'), ('Dancing'), ('Cycling'), ('Reading')
    ON CONFLICT (name) DO NOTHING;
