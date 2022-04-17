package com.example.githubclient.data

import com.example.githubclient.data.entities.UserProfileEntity
import com.example.githubclient.domain.UserRepo
import com.example.githubclient.domain.entities.UserProfile

class UserRepoImpl(private val dao: UsersDAO) : UserRepo {
    override fun getAllUsers(): List<UserProfile> {
        return dao.getAll()
            .map { userEntity ->
                UserProfile(
                    id = userEntity.id,
                    name = userEntity.userName,
                    photo = userEntity.userPhoto
                )
            }
    }

    override fun getUser(id: Int): UserProfile {
        return UserProfile(
            id = dao.getUser(id).id,
            name = dao.getUser(id).userName,
            photo = dao.getUser(id).userPhoto
        )
    }

    override fun saveUser(user: UserProfile) : Boolean {
        dao.insertUser(
            UserProfileEntity(
                id = 0,
                userName = user.name,
                userPhoto = user.photo
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

    override fun deleteAll() {
        dao.deleteAll()
    }
}