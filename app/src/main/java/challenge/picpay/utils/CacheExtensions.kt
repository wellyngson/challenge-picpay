package challenge.picpay.utils

import kotlinx.datetime.Clock

class CacheExtensions {

    companion object {
        private var lastRequisition: Long? = null
        private const val TIME_IN_SECONDS_TO_GET_IN_CACHE = 5

        fun shouldGetDataInCache(): Boolean {
            val value = if (lastRequisition == null) {
                false
            } else {
                (getCurrentHour() - lastRequisition!!) < TIME_IN_SECONDS_TO_GET_IN_CACHE
            }

            lastRequisition = getCurrentHour()

            return value
        }

        private fun getCurrentHour() = Clock.System.now().epochSeconds
    }
}
