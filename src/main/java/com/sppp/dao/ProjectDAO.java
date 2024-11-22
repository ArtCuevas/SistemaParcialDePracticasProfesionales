package com.sppp.dao;

import com.sppp.model.Project;

import java.sql.SQLException;
import java.util.List;

public interface ProjectDAO {
    public void createProject(Project project) throws SQLException;
    public Project readProject(int id) throws SQLException;
    public void updateProject(Project project) throws SQLException;
    public void deletePokemon(Project project) throws SQLException;
    public List<Project> getAllProjects() throws SQLException;
}