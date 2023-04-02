package com.spring.sdjpa.dao;

import com.spring.sdjpa.domain.Author;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import javax.sql.DataSource;
import java.sql.*;

@Component
@RequiredArgsConstructor
public class AuthorDAOImpl implements AuthorDAO {

    private final DataSource dataSource;

    @Override
    public Author getById(Long id) {
        Connection connection = null;
        Statement statement = null;
        PreparedStatement ps = null;
        ResultSet resultSet = null;

        try {
            connection = dataSource.getConnection();
            statement = connection.createStatement();
            ps = connection.prepareStatement("select * from author where id = ?");
            ps.setLong(1, id);
            resultSet = ps.executeQuery();

//            resultSet = statement.executeQuery("select * from author where id = " + id);
            if (resultSet.next()) {
                return getAuthorFromRS(resultSet);
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        } finally {
            try {
                closeAll(resultSet, connection, ps);
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return null;
    }



    @Override
    public Author getByNameAndSurname(String name, String surname) {
        Connection connection = null;
        PreparedStatement ps = null;
        ResultSet resultSet = null;

        try {
            connection = dataSource.getConnection();
            ps = connection.prepareStatement("select * from author where first_name = ? or last_name = ?");
            ps.setString(1, name);
            ps.setString(2, surname);
            resultSet = ps.executeQuery();

            if (resultSet.next()) {
               return getAuthorFromRS(resultSet);
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        } finally {
            try {
                closeAll(resultSet, connection, ps);

            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return null;
    }

    @Override
    public Author saveAuthor(Author author) {
        Connection connection = null;
        PreparedStatement ps = null;
        ResultSet resultSet = null;

        try {
            connection = dataSource.getConnection();
            ps = connection.prepareStatement("insert into author(first_name, last_name) VALUES(?, ?)");

            ps.setString(1, author.getFirstName());
            ps.setString(2, author.getLastName());
            ps.execute();

            Statement statement = connection.createStatement();
            resultSet = statement.executeQuery("select last_insert_id()");

            if (resultSet.next()) {
                Long authId = resultSet.getLong(1);

                return this.getById(authId);
            }

            statement.close();

        } catch (SQLException e) {
            throw new RuntimeException(e);
        } finally {
            try {
                closeAll(resultSet, connection, ps);

            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return null;
    }

    @Override
    public Author updateAuthor(Author author) {
        Connection connection = null;
        PreparedStatement ps = null;
        ResultSet resultSet = null;

        try {
            connection = dataSource.getConnection();
            ps = connection.prepareStatement("update author set first_name = ?, last_name = ? where author.id = ?");

            ps.setString(1, author.getFirstName());
            ps.setString(2, author.getLastName());
            ps.setLong(3, author.getId());
            ps.execute();


        } catch (SQLException e) {
            throw new RuntimeException(e);
        } finally {
            try {
                closeAll(resultSet, connection, ps);
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return this.getById(author.getId());
    }

    @Override
    public void deleteAuthor(Long id) {
        Connection connection = null;
        PreparedStatement ps = null;

        try {
            connection = dataSource.getConnection();
            ps = connection.prepareStatement("delete from author  where author.id = ?");

            ps.setLong(1, id);
            ps.execute();


        } catch (SQLException e) {
            throw new RuntimeException(e);
        } finally {
            try {
                closeAll(null, connection, ps);
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }

    }

    private void closeAll(ResultSet resultSet, Connection connection, PreparedStatement ps) throws SQLException {
        if (resultSet != null) {
            resultSet.close();
        }

        if (ps != null) {
            ps.close();
        }

        if (connection != null) {
            connection.close();
        }
    }

    private Author getAuthorFromRS(ResultSet resultSet) throws SQLException {
        Author author = new Author();
        author.setId(resultSet.getLong("id"));
        author.setLastName(resultSet.getString("last_name"));
        author.setFirstName(resultSet.getString("first_name"));

        return author;
    }

}
