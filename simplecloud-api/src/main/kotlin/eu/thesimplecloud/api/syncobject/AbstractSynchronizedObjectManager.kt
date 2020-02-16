package eu.thesimplecloud.api.syncobject

import eu.thesimplecloud.api.CloudAPI
import eu.thesimplecloud.api.event.syncobject.SynchronizedObjectUpdatedEvent
import java.lang.IllegalArgumentException
import java.lang.IllegalStateException

abstract class AbstractSynchronizedObjectManager : ISynchronizedObjectManager {

    protected val nameToValue: MutableMap<String, ISynchronizedObject> = HashMap()

    override fun updateObject(synchronizedObject: ISynchronizedObject, fromPacket: Boolean) {
        if (nameToValue.containsKey(synchronizedObject.getName())) {
            val cachedObject = nameToValue[synchronizedObject.getName()]!!
            if (cachedObject !== synchronizedObject) {
                if (cachedObject::class.java != synchronizedObject::class.java) throw IllegalArgumentException("Class of registered value by name ${synchronizedObject.getName()} is not matching the class of the update value. Registered: ${cachedObject::class.java.name} Update: ${synchronizedObject::class.java.name}")
                for (field in cachedObject::class.java.declaredFields) {
                    field.isAccessible = true
                    field.set(cachedObject, field.get(synchronizedObject))
                }
            }
            CloudAPI.instance.getEventManager().call(SynchronizedObjectUpdatedEvent(cachedObject))
        } else {
            nameToValue[synchronizedObject.getName()] = synchronizedObject
            CloudAPI.instance.getEventManager().call(SynchronizedObjectUpdatedEvent(synchronizedObject))
        }
    }

    override fun <T : ISynchronizedObject> getObject(name: String): T? {
        return nameToValue[name] as T?
    }

}