package com.example.githubclient.data.localRepo

import com.example.githubclient.data.entities.RepositoriesEntity
import com.example.githubclient.data.entities.UserProfileEntity
import com.example.githubclient.domain.UserRepo
import com.example.githubclient.domain.entities.RepoDTO
import com.example.githubclient.domain.entities.UserDTO

class UserRepoImpl(private val dao: UsersDAO) : UserRepo {
    override fun getAllUsers(): List<UserDTO> {
        return dao.getAllUsers()
            .map { userEntity ->
                UserDTO(
                    id = userEntity.gitHubId,
                    login = userEntity.userName,
                    avatar_url = userEntity.userPhoto
                )
            }
    }

    override fun getUser(id: Long): UserDTO {
        return UserDTO(
            id = dao.getUser(id).gitHubId,
            login = dao.getUser(id).userName,
            avatar_url = dao.getUser(id).userPhoto
        )
    }

    override fun saveUser(user: UserDTO) : Boolean {
        dao.insertUser(
            UserProfileEntity(
                id = 0,
                gitHubId = user.id,
                userName = user.login,
                userPhoto = user.avatar_url
            )
        )
        return true
    }

/*    override fun updateUserData(id: Int, user: UserProfile) {
        TODO("Not yet implemented")
    }*/

    override fun deleteUser(id: Int) {
        dao.delete(id)
    }

    override fun deleteAllUsers() {
        dao.deleteAll()
    }

    override fun getRepositoriesList(id: Long): List<RepoDTO> {
        return dao.getAllRepositories(id)
            .map { repoEntity ->
                RepoDTO(
                    id = repoEntity.id,
                    name = repoEntity.name,
                    userId = repoEntity.userId
                )
            }
    }

    override fun addRepo(repo: RepoDTO): Boolean {
        dao.insertRepo(
            RepositoriesEntity(
                id = 0,
                name = repo.name,
                userId = repo.userId
            )
        )
        return true
    }
}