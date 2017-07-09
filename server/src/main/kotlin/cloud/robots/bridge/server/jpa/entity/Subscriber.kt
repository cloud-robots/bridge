package cloud.robots.bridge.server.jpa.entity

import javax.persistence.*


@Entity
data class Subscriber(@Id val id: String = "",
                      @ManyToMany(cascade = arrayOf(CascadeType.ALL), fetch = FetchType.EAGER)
                      @JoinTable(joinColumns = arrayOf(JoinColumn(name = "subscriber_id", referencedColumnName = "id")),
                          inverseJoinColumns = arrayOf(JoinColumn(name = "topic_id", referencedColumnName = "id")))
                      var topics: List<Topic> = arrayListOf(),
                      @ManyToMany(cascade = arrayOf(CascadeType.ALL), fetch = FetchType.EAGER)
                      @JoinTable(joinColumns = arrayOf(JoinColumn(name = "subscriber_id", referencedColumnName = "id")),
                          inverseJoinColumns = arrayOf(JoinColumn(name = "message_id", referencedColumnName = "id")))
                      var messages : Set<Message> = setOf())
