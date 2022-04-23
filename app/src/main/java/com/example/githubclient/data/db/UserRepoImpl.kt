package com.example.githubclient.data.db

import com.example.githubclient.data.entities.RepositoriesEntity
import com.example.githubclient.data.entities.UserProfileEntity
import com.example.githubclient.domain.UserRepo
import com.example.githubclient.data.entities.RepoDto
import com.example.githubclient.data.entities.UserDto

class UserRepoImpl(private val dao: UsersDAO) : UserRepo {
    override fun getAllUsers(): List<UserDto> {
        return dao.getAllUsers()
            .map { userEntity ->
                UserDto(
                    id = userEntity.gitHubId,
                    login = userEntity.userName,
                    avatar_url = userEntity.userPhoto
                )
            }
    }

    override fun getUser(id: Long): UserDto {
        return UserDto(
            id = dao.getUser(id).gitHubId,
            login = dao.getUser(id).userName,
            avatar_url = dao.getUser(id).userPhoto
        )
    }

    override fun saveUser(user: UserDto) : Boolean {
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

    override fun getRepositoriesList(id: Long): List<RepoDto> {
        return dao.getAllRepositories(id)
            .map { repoEntity ->
                RepoDto(
                    id = repoEntity.id,
                    name = repoEntity.name,
                    userId = repoEntity.userId
                )
            }
    }

    override fun addRepo(repo: RepoDto): Boolean {
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