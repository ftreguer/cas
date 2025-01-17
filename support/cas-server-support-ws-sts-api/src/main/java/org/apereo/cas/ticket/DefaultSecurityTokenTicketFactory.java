package org.apereo.cas.ticket;

import org.apereo.cas.util.EncodingUtils;

import lombok.RequiredArgsConstructor;
import lombok.val;

/**
 * This is {@link DefaultSecurityTokenTicketFactory}.
 *
 * @author Misagh Moayyed
 * @since 5.1.0
 */
@RequiredArgsConstructor
public class DefaultSecurityTokenTicketFactory implements SecurityTokenTicketFactory {

    private final UniqueTicketIdGenerator ticketUniqueTicketIdGenerator;
    private final ExpirationPolicy expirationPolicy;

    @Override
    public TicketFactory get(final Class<? extends Ticket> clazz) {
        return this;
    }

    @Override
    public SecurityTokenTicket create(final TicketGrantingTicket ticket, final byte[] securityTokenSerialized) {
        val token = EncodingUtils.encodeBase64(securityTokenSerialized);
        val id = ticketUniqueTicketIdGenerator.getNewTicketId(SecurityTokenTicket.PREFIX);
        val stt = new DefaultSecurityTokenTicket(id, ticket, this.expirationPolicy, token);
        ticket.getDescendantTickets().add(stt.getId());
        return stt;
    }
}
